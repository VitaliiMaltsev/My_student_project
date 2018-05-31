package edu.javacourse.third.checkers;

import edu.javacourse.third.answer.CheckerAnswer;
import edu.javacourse.third.checkers.answer.BasicCheckerAnswer;
import edu.javacourse.third.domain.Person;
import edu.javacourse.third.domain.PersonAdult;
import edu.javacourse.third.domain.PersonChild;
import edu.javacourse.third.domain.StudentOrder;
import edu.javacourse.third.exception.SendGetDataException;

import javax.xml.stream.XMLOutputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.XMLStreamWriter;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Map;
import java.util.Properties;
import java.util.PropertyResourceBundle;
import java.util.TreeMap;
import java.util.concurrent.Callable;

public class ZagsChecker extends BasicChecker implements Callable<CheckerAnswer> {
    private PersonAdult husband;
    private PersonAdult wife;
    private PersonChild child;
    private PersonAdult person;

    private static Map<String, String> map = new TreeMap<>();

    static {
        PropertyResourceBundle prb = (PropertyResourceBundle) PropertyResourceBundle.getBundle("zags_checker");

        map.put("host", prb.getString("zags.host"));
        map.put("port", prb.getString("zags.port"));
        map.put("login", prb.getString("zags.login"));
        map.put("password", prb.getString("zags.password"));
        System.out.println(prb.getString("zags.test"));
    }

    public ZagsChecker() {
        super(map.get("host"), Integer.parseInt(map.get("port")),
                map.get("login"), map.get("password"));
    }

    public void setParameters(PersonAdult person) {
        this.person = person;
    }

    public void setParameters(PersonAdult husband, PersonAdult wife, PersonChild child) {
        this.husband = husband;
        this.wife = wife;
        this.child = child;
    }

    public void setPerson(PersonAdult person) {
        this.person = person;
    }

    public Person getPerson() {
        return person;
    }

    @Override
    protected CheckerAnswer sendAndGetData() throws SendGetDataException, XMLStreamException {

        if (child == null) {
            return checkWedding();
        } else {
            return checkChild();
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

    private CheckerAnswer checkWedding() throws XMLStreamException {
        System.out.println("ZagsChecker.checkWedding");

        try {
            OutputStream os = socket.getOutputStream();
            StringBuilder sb = new StringBuilder(buildXmlForFamily());
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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BasicCheckerAnswer(true, "ZagsChecker.checkWedding");
    }

    private String buildXmlForFamily() throws XMLStreamException {
        ByteArrayOutputStream boss = new ByteArrayOutputStream();
        StudentOrder so = new StudentOrder();

        XMLOutputFactory factory = XMLOutputFactory.newFactory();
        XMLStreamWriter xml = factory.createXMLStreamWriter(boss);

        xml.writeStartDocument();
        xml.writeStartElement("marriage");
        xml.writeStartElement("person");
        xml.writeStartElement("surName");
        xml.writeCharacters(husband.getSurName());
        xml.writeEndElement();
        xml.writeStartElement("givenName");
        xml.writeCharacters(husband.getGivenName());
        xml.writeEndElement();
        xml.writeStartElement("patronymic");
        xml.writeCharacters(husband.getPatronymic());
        xml.writeEndElement();
        xml.writeStartElement("dateOfBirth");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        xml.writeCharacters(sdf.format(husband.getDateOfBirth()));
        xml.writeEndElement();
        xml.writeEndElement();
        xml.writeStartElement("person");
        xml.writeStartElement("surName");
        xml.writeCharacters(wife.getSurName());
        xml.writeEndElement();
        xml.writeStartElement("givenName");
        xml.writeCharacters(wife.getGivenName());
        xml.writeEndElement();
        xml.writeStartElement("patronymic");
        xml.writeCharacters(wife.getPatronymic());
        xml.writeEndElement();
        xml.writeStartElement("dateOfBirth");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy");
        xml.writeCharacters(sdf1.format(wife.getDateOfBirth()));
        xml.writeEndElement();
        xml.writeEndElement();
        xml.writeEndDocument();
        System.out.println(boss.toString());
        return boss.toString();

    }


    private CheckerAnswer checkChild() throws XMLStreamException {
        System.out.println("ZagsChecker.checkChild:" + child.getSurName() + " " + child.getGivenName());

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
        } catch (IOException e) {
            e.printStackTrace();
        }
        return new BasicCheckerAnswer(true, "ZagsChecker.checkChild");
    }


    private String buildXmlForPerson() throws UnsupportedEncodingException, XMLStreamException {
        //ByteOutputStream bos = new ByteOutputStream();
        ByteArrayOutputStream boss = new ByteArrayOutputStream();
        StudentOrder so = new StudentOrder();

        XMLOutputFactory factory = XMLOutputFactory.newFactory();
        XMLStreamWriter xml = factory.createXMLStreamWriter(boss);

        xml.writeStartDocument();
        xml.writeStartElement("family");
        xml.writeStartElement("person");
        xml.writeStartElement("surName");
        xml.writeCharacters(husband.getSurName());
        xml.writeEndElement();
        xml.writeStartElement("givenName");
        xml.writeCharacters(husband.getGivenName());
        xml.writeEndElement();
        xml.writeStartElement("patronymic");
        xml.writeCharacters(husband.getPatronymic());
        xml.writeEndElement();
        xml.writeStartElement("dateOfBirth");
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        xml.writeCharacters(sdf.format(husband.getDateOfBirth()));
        xml.writeEndElement();
        xml.writeEndElement();
        xml.writeStartElement("person");
        xml.writeStartElement("surName");
        xml.writeCharacters(wife.getSurName());
        xml.writeEndElement();
        xml.writeStartElement("givenName");
        xml.writeCharacters(wife.getGivenName());
        xml.writeEndElement();
        xml.writeStartElement("patronymic");
        xml.writeCharacters(wife.getPatronymic());
        xml.writeEndElement();
        xml.writeStartElement("dateOfBirth");
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd.MM.yyyy");
        xml.writeCharacters(sdf1.format(wife.getDateOfBirth()));
        xml.writeEndElement();
        xml.writeEndElement();

        xml.writeStartElement("person");
        xml.writeStartElement("surName");
        xml.writeCharacters(child.getSurName());
        xml.writeEndElement();
        xml.writeStartElement("givenName");
        xml.writeCharacters(child.getGivenName());
        xml.writeEndElement();
        xml.writeStartElement("patronymic");
        xml.writeCharacters(child.getPatronymic());
        xml.writeEndElement();
        xml.writeStartElement("dateOfBirth");
        SimpleDateFormat sdf2 = new SimpleDateFormat("dd.MM.yyyy");
        xml.writeCharacters(sdf2.format(child.getDateOfBirth()));
        xml.writeEndElement();
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
