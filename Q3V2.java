import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;

import org.junit.Test;

import dragonball.model.dragon.Dragon;
import dragonball.model.exceptions.BelowMinSenzuBeansException;
import dragonball.model.exceptions.DragonBallException;
import dragonball.model.exceptions.MissingFieldException;
import dragonball.model.exceptions.NotEnoughResourcesException;
import dragonball.model.exceptions.UnknownAttackTypeException;
import dragonball.model.game.Game;

public class Q3V2 {
	private int thrown = 1;

	@Test(timeout = 1000)
	public void BelowMinSenzuBeansExceptionExtendsNotEnoughResourcesException()
			throws Exception {
		assertEquals(
				"BelowMinSenzuBeansException class should extend DragonBallException",
				DragonBallException.class,
				BelowMinSenzuBeansException.class.getSuperclass());
	}

	@Test(timeout = 1000)
	public void testBelowMinSenzuBeansExceptionClassVariables()
			throws Exception {
		Field f = null;
		boolean thrown = false;
		try {
			f = BelowMinSenzuBeansException.class
					.getDeclaredField("numberSenzuBeans");
		} catch (NoSuchFieldException e) {
			thrown = true;
		}
		assertFalse(
				"there should be \"numberSenzuBeans\" instance variable in class BelowMinSenzuBeansException",
				thrown);
		assertEquals(
				"\"numberSenzuBeans\" instance variable in class BelowMinSenzuBeansException should be of type int",
				f.getType(), int.class);

	}

	@Test(timeout = 1000)
	public void testBelowMinSenzuBeansExceptionClassVariablesAccessibility()
			throws Exception {

		Field f = BelowMinSenzuBeansException.class
				.getDeclaredField("numberSenzuBeans");
		assertEquals(
				"\"numberSenzuBeans\" instance variable in class BelowMinSenzuBeansException should not be accessed outside that class",
				2, f.getModifiers());

	}

	@Test(timeout = 1000)
	public void testBelowMinSenzuBeansExceptionClassREADVariables()
			throws Exception {
		Method[] methods = BelowMinSenzuBeansException.class
				.getDeclaredMethods();

		assertTrue(
				"The \"numberSenzuBeans\" instance variable in class BelowMinSenzuBeansException is a READ variable.",
				containsMethodName(methods, "getNumberSenzuBeans"));

		try {
			Method m = BelowMinSenzuBeansException.class
					.getDeclaredMethod("getNumberSenzuBeans");
			assertTrue(
					"incorrect return type for \"getNumberSenzuBeans\" method in BelowMinSenzuBeansException class.",
					m.getReturnType().equals(int.class));
		} catch (Exception e) {
			fail("Missing \"getNumberSenzuBeans\" method in BelowMinSenzuBeansException class which takes no input parameters.");
		}

		assertFalse(
				"The \"numberSenzuBeans\" instance variable in class BelowMinSenzuBeansException is a READ ONLY variable.",
				containsMethodName(methods, "setNumberSenzuBeans"));

	}

	@Test(timeout = 1000)
	public void testBelowMinSenzuBeansExceptionFirstConstructor()
			throws Exception {
		Class<BelowMinSenzuBeansException> aClass = BelowMinSenzuBeansException.class;
		boolean thrown = false;
		try {
			aClass.getConstructor(new Class[] { int.class });
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		int testRandom = (int) (Math.random() * 10);
		BelowMinSenzuBeansException b = new BelowMinSenzuBeansException(
				testRandom);
		Field var = BelowMinSenzuBeansException.class
				.getDeclaredField("numberSenzuBeans");
		var.setAccessible(true);
		assertFalse(
				"Missing constructor with 1 parameter in BelowMinSenzuBeansException class.",
				thrown);
		assertTrue(
				"Constructor with 1 parameter should set the message of the exception, the message of the exception should include the number of senzu beans of the dragon you are trying to load.",
				b.getMessage().contains("" + testRandom));
		assertEquals(
				"Constructor with 1 parameter should set the numberSenzuBeans variable.",
				testRandom, var.get(b));
	}

	@Test(timeout = 1000)
	public void testBelowMinSenzuBeansExceptionSecondConstructor()
			throws Exception {
		Class<BelowMinSenzuBeansException> aClass = BelowMinSenzuBeansException.class;
		boolean thrown = false;
		try {
			aClass.getConstructor(new Class[] { String.class, int.class });
		} catch (NoSuchMethodException e) {
			thrown = true;
		}
		assertFalse(
				"Missing constructor with 2 parameters in BelowMinSenzuBeansException class.",
				thrown);
		int testRandom = (int) (Math.random() * 10);

		String testMessage = "test" + (int) (Math.random() * 10000);
		BelowMinSenzuBeansException b = new BelowMinSenzuBeansException(
				testMessage, testRandom);
		Field var = BelowMinSenzuBeansException.class
				.getDeclaredField("numberSenzuBeans");
		var.setAccessible(true);
		assertTrue(
				"Constructor with 2 parameters should set the message of the exception.",
				b.getMessage().contains(testMessage));
		assertTrue(
				"The message of the exception should include the number of senzu beans of the dragon you are trying to load.",
				b.getMessage().contains("" + testRandom));
		assertEquals(
				"Constructor with 2 parameters should set the numberSenzuBeans variable.",
				testRandom, var.get(b));
	}

	@Test(timeout = 1000)
	public void testThrow_BelowMinSenzuBeansException_OnLoadDragons()
			throws Exception {

		Game g = new Game();

		String testFile = "";
		PrintWriter attacksWriter;
		File f;
		int senzuTested = 0;

		for (int i = 0; i < 5; i++) {
			senzuTested = i;
			testFile = "Test" + (Math.random() * 10000) + ".csv";
			attacksWriter = new PrintWriter(testFile);
			attacksWriter.println("Shenron," + senzuTested + ",5");
			attacksWriter.println("Big Bang , Death Beam");
			attacksWriter
					.println("Super Big Kamehameha,Final Attack,Final Gun");

			attacksWriter.close();
			boolean thrown = false;

			try {
				g.loadDragons(testFile);
			} catch (BelowMinSenzuBeansException e) {
				thrown = true;
			}

			f = new File(testFile);
			f.delete();

			assertTrue(
					"BelowMinSenzuBeansException should be thrown while loading the dragons if the number of senzu beans is less than 5.",
					thrown);
		}

		senzuTested = 5;
		attacksWriter = new PrintWriter(testFile);
		attacksWriter.println("Shenron," + senzuTested + ",5");
		attacksWriter.println("Big Bang , Death Beam");
		attacksWriter.println("Super Big Kamehameha,Final Attack,Final Gun");

		attacksWriter.close();

		try {
			g.loadDragons(testFile);
		} catch (BelowMinSenzuBeansException e) {
			fail("BelowMinSenzuBeansException should not be thrown while loading the dragons if the number of senzu beans is equal or greater than 5.");
		}

		f = new File(testFile);
		f.delete();

	}

	@Test(timeout = 1000)
	public void testThrownVariables_BelowMinSenzuBeansException_OnLoadDragons()
			throws Exception {

		Game g = new Game();

		String testFile = "Test" + (Math.random() * 10000) + ".csv";
		PrintWriter attacksWriter = new PrintWriter(testFile);

		int senzuTested = 1;

		attacksWriter.println("Shenron," + senzuTested + ",5");
		attacksWriter.println("Big Bang , Death Beam");
		attacksWriter.println("Super Big Kamehameha,Final Attack,Final Gun");

		attacksWriter.close();
		boolean senzu = false;
		boolean message = false;

		try {
			g.loadDragons(testFile);
		} catch (BelowMinSenzuBeansException e) {
			Field var = BelowMinSenzuBeansException.class
					.getDeclaredField("numberSenzuBeans");
			var.setAccessible(true);
			if ((int) var.get(e) == senzuTested)
				senzu = true;
			if (e.getMessage().contains(senzuTested + ""))
				message = true;

		}

		File f = new File(testFile);
		f.delete();
		assertTrue(
				"The number of senzu beans of the dragon failed to load should be set in BelowMinSenzuBeansException.",
				senzu);
		assertTrue(
				"The message of BelowMinSenzuBeansException should include number of senzu beans of the dragon you are trying to load.",
				message);

	}

	@Test(timeout = 1000)
	public void testGameConstructor_Handles_BelowMinSenzuBeansException()
			throws Exception {
		thrown = 0;
		Game g = null;

		final String testMessage = "Test" + (Math.random() * 10000) + "Message";
		final int senzuTested = (int) (Math.random() * 5);

		try {
			g = new Game() {

				public void loadDragons(String f) throws RuntimeException,
						BelowMinSenzuBeansException {

					if (thrown == 0) {
						thrown++;
						throw new BelowMinSenzuBeansException(testMessage,
								senzuTested);
					}

				}
			};

		} catch (Exception e) {
			if (e instanceof BelowMinSenzuBeansException)
				fail("Game constructor should handle BelowMinSenzuBeansException thrown on Loading Dragons");
			e.printStackTrace();
			fail("An exception occurred while loading dragons."
					+ e.getMessage());
		}

	}

	@Test (timeout = 1000)
	public void testGameConstructor_Catch_BelowMinSenzuBeansException()
			throws Exception {
		thrown = 1;
		Game g = null;

		final String testMessage = "Test" + (Math.random() * 10000) + "Message";
		final int senzuTested = (int) (Math.random() * 5);

		try {
			g = new Game() {

				public void loadDragons(String f) throws RuntimeException,
						BelowMinSenzuBeansException {
					if (thrown == 1) {
						thrown++;
						throw new BelowMinSenzuBeansException(testMessage,
								senzuTested);
					}

					try {
						super.loadDragons(f);
					} catch (Exception e) {
						e.printStackTrace();
						fail("An exception occurred while loading dragons."
								+ e.getMessage());
					}

				}
			};

		} catch (Exception e) {
			if (e instanceof BelowMinSenzuBeansException)
				fail("Game constructor should handle BelowMinSenzuBeansException thrown on Loading Dragons");
			e.printStackTrace();
			fail("An exception occurred while loading dragons."
					+ e.getMessage());
		}

		ArrayList<Dragon> testDragons = g.getDragons();

		assertEquals(
				"Load Dragons should load from the auxiliary csv file when an exception is thrown",
				1, testDragons.size());

		assertEquals(
				"Dragon name is not loaded correctly from the auxiliary CSV file of Dragons",
				"Shenron", testDragons.get(0).getName());

		assertEquals(
				"Dragon senzuBeans is not loaded correctly from the auxiliary CSV file of Dragons",
				10, testDragons.get(0).getSenzuBeans());

		assertEquals(
				"Dragon ability points is not loaded correctly from the auxiliary CSV file of Dragons",
				5, testDragons.get(0).getAbilityPoints());

		assertEquals(
				"Super Attacks of Dragon doesn't contain the same number of super attacks given in the auxiliary CSV file",
				2, testDragons.get(0).getSuperAttacks().size());
		assertEquals(
				"Super Attack of Dragon is not loaded correctly from the auxiliary CSV file of Dragons",
				"Big Bang Kamehameha", testDragons.get(0).getSuperAttacks()
						.get(0).getName());
		assertEquals(
				"Super Attack of Dragon is not loaded correctly from the auxiliary CSV file of Dragons",
				"Emperor's Death Beam", testDragons.get(0).getSuperAttacks()
						.get(1).getName());

		assertEquals(
				"Ultimate Attacks of Dragon doesn't contain the same number of ultimate attacks given in the auxiliary CSV file",
				3, testDragons.get(0).getUltimateAttacks().size());
		assertEquals(
				"Ultimate Attack of Dragon is not loaded correctly from the auxiliary CSV file of Dragons",
				"Super Big Bang Kamehameha", testDragons.get(0)
						.getUltimateAttacks().get(0).getName());
		assertEquals(
				"Ultimate Attack of Dragon is not loaded correctly from the auxiliary CSV file of Dragons",
				"Final Shine Attack", testDragons.get(0).getUltimateAttacks()
						.get(1).getName());
		assertEquals(
				"Ultimate Attack of Dragon is not loaded correctly from the auxiliary CSV file of Dragons",
				"Final Galick Gun", testDragons.get(0).getUltimateAttacks()
						.get(2).getName());

	}

	public static boolean containsMethodName(Method[] methods, String name) {
		for (Method method : methods) {
			if (method.getName().equals(name))
				return true;
		}
		return false;
	}
}
