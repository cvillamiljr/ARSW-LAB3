/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.test.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.junit.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.*;

/**
 *
 * @author hcadavid
 */
public class InMemoryPersistenceTest {
    
    @Test
    public void saveNewAndLoadTest() throws BlueprintPersistenceException, BlueprintNotFoundException{
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();

        Point[] pts0=new Point[]{new Point(40, 40),new Point(15, 15)};
        Blueprint bp0=new Blueprint("mack", "mypaint",pts0);
        
        ibpp.saveBlueprint(bp0);
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        ibpp.saveBlueprint(bp);
        
        assertNotNull("Loading a previously stored blueprint returned null.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()));
        
        assertEquals("Loading a previously stored blueprint returned a different blueprint.",ibpp.getBlueprint(bp.getAuthor(), bp.getName()), bp);
        
    }


    @Test
    public void saveExistingBpTest() {
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        try {
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        Point[] pts2=new Point[]{new Point(10, 10),new Point(20, 20)};
        Blueprint bp2=new Blueprint("john", "thepaint",pts2);

        try{
            ibpp.saveBlueprint(bp2);
            fail("An exception was expected after saving a second blueprint with the same name and autor");
        }
        catch (BlueprintPersistenceException ex){
            
        }
                
        
    }
    
    
    
    
    
    
    
    
    @Test
    public void getBlueprintTest() throws BlueprintNotFoundException {
    	//crea usando BlueprintsServices
    	ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
    	BlueprintsServices Sibpp = ac.getBean(BlueprintsServices.class);
    	
    	//crea usando InMemoryBlueprintPersistence directamente
    	InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        
        try {
        	//añade usando BlueprintsServices
            Sibpp.addNewBlueprint(bp);
            //añade usando InMemoryBlueprintPersistence directamente
            ibpp.saveBlueprint(bp);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        assertEquals(Sibpp.getBlueprint("john", "thepaint"),ibpp.getBlueprint("john", "thepaint"));
        
    }
    
    @Test
    public void getBlueprintsByAuthorTest() throws BlueprintNotFoundException {
    	//crea usando BlueprintsServices
    	ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
    	BlueprintsServices Sibpp = ac.getBean(BlueprintsServices.class);
    	
    	//crea usando InMemoryBlueprintPersistence directamente
    	InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        Point[] pts1=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp1=new Blueprint("sam", "pintura1",pts1);
        Point[] pts2=new Point[]{new Point(0, 0),new Point(10, 10)};
        Blueprint bp2=new Blueprint("sam", "pintura2",pts2);
        
        try {
        	//añade usando BlueprintsServices
            Sibpp.addNewBlueprint(bp);
            Sibpp.addNewBlueprint(bp1);
            Sibpp.addNewBlueprint(bp2);
            //añade usando InMemoryBlueprintPersistence directamente
            ibpp.saveBlueprint(bp);
            ibpp.saveBlueprint(bp1);
            ibpp.saveBlueprint(bp2);
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        assertEquals(Sibpp.getBlueprintsByAuthor("john"),ibpp.getBlueprintsByAuthor("john"));
        assertEquals(Sibpp.getBlueprintsByAuthor("sam"),ibpp.getBlueprintsByAuthor("sam"));
                
    }


    
    
    @Test
    public void RedundancyFilteringTest() throws BlueprintNotFoundException{
    	//crea usando BlueprintsServices
    	ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
    	BlueprintsServices BS = ac.getBean(BlueprintsServices.class);
    	
    	ApplicationContext acc = new ClassPathXmlApplicationContext("applicationContext.xml");
    	BlueprintsServices BS1 = acc.getBean(BlueprintsServices.class);
    	
    	Point p1 = new Point(0, 0);
    	Point p2 = new Point(10, 10);
    	Point p3 = new Point(10, 10);
    	Point p4 = new Point(10, 10);
    	Point p5 = new Point(10, 15);
    	
    	String name = "john";
    	String autor = "thepaint";
    	
        Blueprint bp=new Blueprint(name, autor);
        Blueprint bp1=new Blueprint(name, autor);
        
        bp.addPoint(p1);
        bp.addPoint(p2);
        bp.addPoint(p3);
        bp.addPoint(p4);
        bp.addPoint(p5);
        
        bp1.addPoint(p1);
        bp1.addPoint(p2);
        bp1.addPoint(p5);
        
        try {
            BS.addNewBlueprint(bp);
            BS1.addNewBlueprint(bp1);
  
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }

        BS.filtrar();
        
                
    }
    
    
}
