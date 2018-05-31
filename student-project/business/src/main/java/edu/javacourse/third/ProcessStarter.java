package edu.javacourse.third;

import edu.javacourse.third.answer.CheckerAnswer;
import edu.javacourse.third.checkers.GrnChecker;
import edu.javacourse.third.checkers.StudentChecker;
import edu.javacourse.third.checkers.ZagsChecker;
import edu.javacourse.third.db.FactoryDataSource;
import edu.javacourse.third.db.FakeDataSource;
import edu.javacourse.third.db.JaxbReader;
import edu.javacourse.third.db.StudentOrderDataSource;
import edu.javacourse.third.domain.PersonChild;
import edu.javacourse.third.domain.StudentOrder;
import edu.javacourse.third.domain.StudentOrders;
import edu.javacourse.third.exception.CheckException;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * Created by antonsaburov on 22.02.17.
 */
public class ProcessStarter {
    public static void main(String[] params) {
        ProcessStarter t = new ProcessStarter();
        t.processList();
    }

    public void processList() {
        StudentOrderDataSource ds = FactoryDataSource.getDataSource();
        //ds.addStudentOrder(null);
        List<StudentOrder> orderList = ds.getStudentOrders();
        StudentOrders sors = new StudentOrders();
        JaxbReader jaxbReader = new JaxbReader();
//        List<StudentOrder> orderList = jaxbReader.readXml();
//        for (StudentOrder so : orderList) {
//            processStudentOrder(so);
//        }
    }

    private void processStudentOrder(StudentOrder so) {
        List<CheckerAnswer> answers = new ArrayList<>();

        try {
            answers.addAll(checkGrn(so));
            answers.addAll(checkStudent(so));
            answers.addAll(checkZags(so));
        } catch (CheckException ex) {
            // TODO Сделать обработку ошибки - что-то записать в базу
            return;
        }

        ApproveManager approveManager = new ApproveManager();
        for (CheckerAnswer ca : answers) {
            if (!ca.getResult()) {
                approveManager.denyOrder(so, answers);
                return;
            }
        }
        approveManager.approveOrder(so, answers);
    }

    private List<CheckerAnswer> checkGrn(StudentOrder so) throws CheckException {
        List<CheckerAnswer> answers = new ArrayList<>();

        ExecutorService es = Executors.newFixedThreadPool(4);

        List<Future<CheckerAnswer>> result = new ArrayList<>();
        GrnChecker grnH = new GrnChecker(so.getHusband());
        result.add(es.submit(grnH));
        GrnChecker grnW = new GrnChecker(so.getWife());
        result.add(es.submit(grnW));
        for (PersonChild pc : so.getChildren()) {
            GrnChecker grnC = new GrnChecker(pc);
            result.add(es.submit(grnC));
        }
        for (Future<CheckerAnswer> f : result) {
            try {
                CheckerAnswer answer = f.get();
                answers.add(answer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        es.shutdown();
        return answers;
    }

    private List<CheckerAnswer> checkStudent(StudentOrder so) throws CheckException {
        List<CheckerAnswer> answers = new ArrayList<>();
        ExecutorService es = Executors.newFixedThreadPool(2);
        List<Future<CheckerAnswer>> result = new ArrayList<>();

        StudentChecker scH = new StudentChecker(so.getHusband());
        result.add(es.submit(scH));
        StudentChecker scW = new StudentChecker(so.getWife());
        result.add(es.submit(scW));
        for (Future<CheckerAnswer> f : result) {
            try {
                CheckerAnswer answer = f.get();
                answers.add(answer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        es.shutdown();
        return answers;
    }

    private List<CheckerAnswer> checkZags(StudentOrder so) throws CheckException {
        List<CheckerAnswer> answers = new ArrayList<>();
        ExecutorService es = Executors.newFixedThreadPool(4);
        List<Future<CheckerAnswer>> result = new ArrayList<>();


        ZagsChecker zc = new ZagsChecker();
        zc.setParameters(so.getHusband(), so.getWife(), null);
        result.add(es.submit(zc));
        for (PersonChild pc : so.getChildren()) {
            ZagsChecker zc1 = new ZagsChecker();
            zc1.setParameters(so.getHusband(), so.getWife(), pc);
            result.add(es.submit(zc1));
        }
        for (Future<CheckerAnswer> f : result) {
            try {
                CheckerAnswer answer = f.get();
                answers.add(answer);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        es.shutdown();
        return answers;

    }
}
