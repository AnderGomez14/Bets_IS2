package dataAccess;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.TypedQuery;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.Bet;
import domain.Event;
import domain.Options;
import domain.Question;
import domain.Registro;
import exceptions.QuestionAlreadyExist;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;

	ConfigXML c;

	public DataAccess(boolean initializeMode) {

		c = ConfigXML.getInstance();

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode)
			fileName = fileName + ";drop";

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}
	}

	public DataAccess() {
		new DataAccess(false);
	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();
		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}

			Event ev1 = new Event(1, "Atlético-Athletic", UtilDate.newDate(year, month, 17));
			Event ev2 = new Event(2, "Eibar-Barcelona", UtilDate.newDate(year, month, 17));
			Event ev3 = new Event(3, "Getafe-Celta", UtilDate.newDate(year, month, 17));
			Event ev4 = new Event(4, "Alavés-Deportivo", UtilDate.newDate(year, month, 17));
			Event ev5 = new Event(5, "Español-Villareal", UtilDate.newDate(year, month, 17));
			Event ev6 = new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 17));
			Event ev7 = new Event(7, "Malaga-Valencia", UtilDate.newDate(year, month, 17));
			Event ev8 = new Event(8, "Girona-Leganés", UtilDate.newDate(year, month, 17));
			Event ev9 = new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year, month, 17));
			Event ev10 = new Event(10, "Betis-Real Madrid", UtilDate.newDate(year, month, 17));

			Event ev11 = new Event(11, "Atletico-Athletic", UtilDate.newDate(year, month, 1));
			Event ev12 = new Event(12, "Eibar-Barcelona", UtilDate.newDate(year, month, 1));
			Event ev13 = new Event(13, "Getafe-Celta", UtilDate.newDate(year, month, 1));
			Event ev14 = new Event(14, "Alavés-Deportivo", UtilDate.newDate(year, month, 1));
			Event ev15 = new Event(15, "Español-Villareal", UtilDate.newDate(year, month, 1));
			Event ev16 = new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 1));

			Event ev17 = new Event(17, "Málaga-Valencia", UtilDate.newDate(year, month, 28));
			Event ev18 = new Event(18, "Girona-Leganés", UtilDate.newDate(year, month, 28));
			Event ev19 = new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year, month, 28));
			Event ev20 = new Event(20, "Betis-Real Madrid", UtilDate.newDate(year, month, 28));

			Question q1;
			Question q2;
			Question q3;
			Question q4;
			Question q5;
			Question q6;

			Options o1;
			Options o2;
			Options o3;

			Registro admin = new Registro("admin", "admin", "79133379Q", "admin@sinkingsoft.com", 21);
			Registro user = new Registro("user", "user", "35743378F", "user@gmail.com", 18);

			if (Locale.getDefault().equals(new Locale("es"))) {
				q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
				q2 = ev1.addQuestion("¿Quién meterá el primer gol?", 2);
				q3 = ev11.addQuestion("¿Quién ganará el partido?", 1);
				q4 = ev11.addQuestion("¿Cuántos goles se marcarán?", 2);
				q5 = ev17.addQuestion("¿Quién ganará el partido?", 1);
				q6 = ev17.addQuestion("¿Habrá goles en la primera parte?", 2);
			} else if (Locale.getDefault().equals(new Locale("en"))) {
				q1 = ev1.addQuestion("Who will win the match?", 1);
				q2 = ev1.addQuestion("Who will score first?", 2);
				q3 = ev11.addQuestion("Who will win the match?", 1);
				q4 = ev11.addQuestion("How many goals will be scored in the match?", 2);
				q5 = ev17.addQuestion("Who will win the match?", 1);
				q6 = ev17.addQuestion("Will there be goals in the first half?", 2);
			} else {
				q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
				q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
				q3 = ev11.addQuestion("Zeinek irabaziko du partidua?", 1);
				q4 = ev11.addQuestion("Zenbat gol sartuko dira?", 2);
				q5 = ev17.addQuestion("Zeinek irabaziko du partidua?", 1);
				q6 = ev17.addQuestion("Golak sartuko dira lehenengo zatian?", 2);

			}

			db.persist(q1);
			db.persist(q2);
			db.persist(q3);
			db.persist(q4);
			db.persist(q5);
			db.persist(q6);

			db.persist(ev1);
			db.persist(ev2);
			db.persist(ev3);
			db.persist(ev4);
			db.persist(ev5);
			db.persist(ev6);
			db.persist(ev7);
			db.persist(ev8);
			db.persist(ev9);
			db.persist(ev10);
			db.persist(ev11);
			db.persist(ev12);
			db.persist(ev13);
			db.persist(ev14);
			db.persist(ev15);
			db.persist(ev16);
			db.persist(ev17);
			db.persist(ev18);
			db.persist(ev19);
			db.persist(ev20);

			db.getTransaction().commit();

			db.getTransaction().begin();
			db.persist(admin);
			db.persist(user);
			o1 = new Options(q1.getQuestionNumber(), "Athletic", Float.valueOf("1.1"));
			o2 = new Options(q1.getQuestionNumber(), "X", Float.valueOf("1.6"));
			o3 = new Options(q1.getQuestionNumber(), "Atletico", Float.valueOf("2.7"));

			db.persist(o1);
			db.persist(o2);
			db.persist(o3);
			db.getTransaction().commit();

			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);

		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question))
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);
		// db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions
						// property of Event class
						// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}

	public void updateQuestion(List<Options> op) {
		TypedQuery<Options> query = db.createQuery("SELECT op FROM Options op WHERE op.questionID= ?1", Options.class);
		query.setParameter(1, op.get(0).getQuestionID());
		List<Options> og = query.getResultList();
		db.getTransaction().begin();
		for (int i = 0; i < og.size(); i++) {
			db.remove(og.get(i));
		}
		db.getTransaction().commit();
		db.getTransaction().begin();
		for (int i = 0; i < op.size(); i++) {
			db.persist(op.get(i));
		}
		db.getTransaction().commit();
	}

	public void addEvent(String nombre, Date date) throws QuestionAlreadyExist {
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev", Event.class);
		List<Event> events = query.getResultList();
		int id = events.size() + 1;
		System.out.println(">> DataAccess: createEvent=> nombre= " + nombre + " Date= " + date + " id=" + id);
		db.getTransaction().begin();
		Event ev = new Event(id, nombre, date);
		db.persist(ev);
		db.getTransaction().commit();
	}

	public int addUser(String dni, String user, String mail, String pwd, int age) {

		TypedQuery<Registro> query1 = db.createQuery("SELECT rg FROM Registro rg WHERE rg.mail ='" + mail + "'",
				Registro.class);
		if (!query1.getResultList().isEmpty())
			return 1;
		TypedQuery<Registro> query2 = db.createQuery("SELECT rg FROM Registro rg WHERE rg.nick ='" + user + "'",
				Registro.class);
		if (!query2.getResultList().isEmpty())
			return 2;
		TypedQuery<Registro> query3 = db.createQuery("SELECT rg FROM Registro rg WHERE rg.dni ='" + dni + "'",
				Registro.class);
		if (!query3.getResultList().isEmpty())
			return 3;

		Registro u = new Registro(user, pwd, dni, mail, age);

		db.getTransaction().begin();
		db.persist(u);
		db.getTransaction().commit();
		return 0;
	}

	public Registro login(String mail, String pwd) {
		TypedQuery<Registro> query = db.createQuery(
				"SELECT rg FROM Registro rg WHERE rg.mail ='" + mail + "' AND rg.pw = '" + pwd + "'", Registro.class);
		List<Registro> events = query.getResultList();

		if (events.isEmpty()) {
			return null;
		} else {
			return events.get(0);
		}
	}

	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2", Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public List<Options> getOptionsQuestion(Question q) {
		TypedQuery<Options> query = db.createQuery("SELECT op FROM Options op WHERE op.questionID= ?1", Options.class);
		query.setParameter(1, q.getQuestionNumber());
		List<Options> op = query.getResultList();
		return op;
	}

	public List<Bet> getBetOptions(Options o) {
		TypedQuery<Bet> query = db.createQuery("SELECT b FROM Bet b WHERE b.optionID= ?1", Bet.class);
		query.setParameter(1, o.getId());
		List<Bet> bet = query.getResultList();
		return bet;
	}

	public void updateMoney(Bet bet, float cuota) {
		float profit = cuota * bet.getCantidadApostada();
		TypedQuery<Registro> query = db.createQuery("SELECT r FROM Registro r WHERE r.dni= ?1", Registro.class);
		query.setParameter(1, bet.getUserDNI());
		Registro user = query.getSingleResult();
		float saldo = user.getSaldo();
		db.getTransaction().begin();
		System.out.println(user.getSaldo());
		user.setSaldo(saldo + profit);
		System.out.println(user.getSaldo());
		db.getTransaction().commit();
	}

	public void updateEvent(Event e) {
		db.getTransaction().begin();
		Event ev = db.find(Event.class, e);
		ev.setFinished(true);
		db.getTransaction().commit();
	}

	public void updateUser(Registro user) {
		TypedQuery<Options> query = db.createQuery("SELECT us FROM Registro us WHERE us.dni= ?1", Options.class);
		query.setParameter(1, user.getDni());
		List<Options> og = query.getResultList();
		db.getTransaction().begin();
		for (int i = 0; i < og.size(); i++) {
			db.remove(og.get(i));
		}
		db.getTransaction().commit();
		db.getTransaction().begin();
		db.persist(user);
		db.getTransaction().commit();
	}

	public void newBet(Bet b) {
		db.getTransaction().begin();
		db.persist(b);
		db.getTransaction().commit();
	}

	public List<Bet> getBet(String user) {
		TypedQuery<Bet> query = db.createQuery("SELECT rg FROM Bet rg WHERE rg.userDNI ='" + user + "'", Bet.class);
		List<Bet> og = query.getResultList();
		return og;
	}

	public Options getOption(int id) {
		TypedQuery<Options> query = db.createQuery("SELECT rg FROM Options rg WHERE rg.id ='" + id + "'",
				Options.class);
		List<Options> og = query.getResultList();
		return og.get(0);
	}

	public Question getQuestion(int id) {
		TypedQuery<Question> query = db.createQuery("SELECT rg FROM Question rg WHERE rg.questionNumber ='" + id + "'",
				Question.class);
		List<Question> og = query.getResultList();
		return og.get(0);
	}
}
