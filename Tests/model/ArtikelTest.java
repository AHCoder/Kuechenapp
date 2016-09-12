package model;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import view.Einheit;

public class ArtikelTest {

	private Artikel meinArtikel;

	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
	}

	@Before
	public void setUp() throws Exception {
		meinArtikel = new Artikel("Apfel",Einheit.KILOGRAMM, 10);
	}

	@Test
	public void testArtikel() {
		fail("Not yet implemented");
	}

	@Test
	public void testToXML() {
		assertEquals(meinArtikel.toXML(),
				"<zutat>\n" + "<menge>10</menge>\n" + "<masseinheit>KILOGRAMM</masseinheit>\n" + "<name>Apfel</name>\n" + "</zutat>\n");
	}

	@Test
	public void testGetName() {
		
		
		assertEquals(meinArtikel.getName(),"Apfel");
		
	}

	@Test
	public void testSetName() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMasseinheit() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetMasseinheit() {
		fail("Not yet implemented");
	}

	@Test
	public void testGetMenge() {
		fail("Not yet implemented");
	}

	@Test
	public void testSetMenge() {
		fail("Not yet implemented");
	}

}
