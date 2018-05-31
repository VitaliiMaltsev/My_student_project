package edu.javacourse.third.checkers;

import edu.javacourse.third.answer.CheckerAnswer;
import edu.javacourse.third.checkers.answer.BasicCheckerAnswer;
import edu.javacourse.third.domain.Person;
import edu.javacourse.third.domain.PersonAdult;
import edu.javacourse.third.exception.SendGetDataException;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Locale;
import java.util.Map;
import java.util.PropertyResourceBundle;
import java.util.TreeMap;
import java.util.concurrent.Callable;

public class StudentChecker extends BasicChecker implements Callable<CheckerAnswer>
{
    private static Map<String,String> map = new TreeMap<>();
    static {
        PropertyResourceBundle pr=(PropertyResourceBundle)
                PropertyResourceBundle.getBundle("student_checker",new Locale("en"));
        map.put("host",pr.getString("student.host"));
        map.put("port",pr.getString("student.port"));
        map.put("login",pr.getString("student.login"));
        map.put("password",pr.getString("student.password"));
        String test = pr.getString("student.test");
        System.out.println(test);
    }
    private PersonAdult person;

    public StudentChecker(PersonAdult person) {
        super(map.get("host"), Integer.parseInt(map.get("port")),
                map.get("login"), map.get("password"));
        this.person=person;
    }

    public void setPerson(PersonAdult person) {
        this.person = person;
    }

    protected CheckerAnswer sendAndGetData() throws SendGetDataException {
        try {
            OutputStream os = socket.getOutputStream();
            StringBuilder sb = new StringBuilder(buildXmlForPerson());
            os.write(sb.toString().getBytes());
            os.flush();

            StringBuilder ans = new StringBuilder();
            Reader br = new InputStreamReader(socket.getInputStream());
            char[] request = new char[6];
            int count = br.read(request);
            while (count != -1) {
                ans.append(new String(request, 0, count));
                if (ans.toString().endsWith("</answer>")) {
                    break;
                }
                count = br.read(request);
            }
            CheckerAnswer answer = buildAnswer(ans.toString());
            System.out.println(answer.toString());
            return answer;
        } catch (IOException | XMLStreamException e) {
            e.printStackTrace();
            throw new SendGetDataException(e.getMessage());
        }
    }

    private CheckerAnswer buildAnswer(String s) {
        // System.out.println(s);
        final String RES_B = "<result>";
        final String RES_E = "</result>";
        final String MES_B = "<message>";
        final String MES_E = "</message>";

        int r1 = s.indexOf(RES_B);
        int r2 = s.indexOf(RES_E);
        int m1 = s.indexOf(MES_B);
        int m2 = s.indexOf(MES_E);
        boolean result = Boolean.parseBoolean(s.substring(r1 + RES_B.length(), r2));
        String message = s.substring(m1 + MES_B.length(), m2);

        BasicCheckerAnswer answer = new BasicCheckerAnswer(result, message);
        return answer;
    }

    private String buildXmlForPerson() throws XMLStreamException {
        //ByteOutputStream bos = new ByteOutputStream();
        ByteArrayOutputStream boss = new ByteArrayOutputStream();

        XMLOutputFactory factory = XMLOutputFactory.newFactory();
        XMLStreamWriter xml = factory.createXMLStreamWriter(boss);

        xml.writeStartDocument();
        xml.writeStartElement("person");
        xml.writeStartElement("surName");
        xml.writeCharacters(person.getSurName());
        xml.writeEndElement();
        xml.writeStartElement("givenName");
        xml.writeCharacters(person.getGivenName());
        xml.writeEndElement();
        xml.writeStartElement("patronymic");
        xml.writeCharacters(person.getPatronymic());
        xml.writeEndElement();
        xml.writeStartElement("dateOfBirth");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        xml.writeCharacters(sdf.format(person.getDateOfBirth()));
        xml.writeEndElement();

        xml.writeEndElement();
        xml.writeEndDocument();

        System.out.println(boss.toString());
        return boss.toString();
    }

    @Override
    public CheckerAnswer call() throws Exception {
        return check();
    }
}