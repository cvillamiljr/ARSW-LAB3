/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.model;

import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import edu.eci.arsw.blueprints.services.BlueprintsServices;

import java.util.HashSet;
import java.util.Set;

import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 *
 * @author diego.puerto
 */
public class Main {

    public static void main(String a[]) throws BlueprintNotFoundException {
    	ApplicationContext ac = new ClassPathXmlApplicationContext("applicationContext.xml");
    	BlueprintsServices BS = ac.getBean(BlueprintsServices.class);
        /*
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Point[] pts1=new Point[]{new Point(30, 30),new Point(40, 40)};
        Point[] pts2=new Point[]{new Point(50, 50),new Point(60, 60)};
        
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        Blueprint bp1=new Blueprint("john", "thepaint1",pts1);
        Blueprint bp2=new Blueprint("David", "thepaint2",pts2);
        
        try {
        	BS.addNewBlueprint(bp);
        	BS.addNewBlueprint(bp1);
        	BS.addNewBlueprint(bp2);
            
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        System.out.println("Creada usando BlueprintsServices y buscando con autor john:");
        System.out.println(BS.getBlueprintsByAuthor("john") + " ");
        */
        
        
        
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10),new Point(10, 10), new Point(10, 10),new Point(10, 15)};
        //Point[] pts1=new Point[]{new Point(30, 30),new Point(40, 40)};
        
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        //Blueprint bp1=new Blueprint("sam", "thepaint1",pts1);
        
        try {
            BS.addNewBlueprint(bp);
            //BS.addNewBlueprint(bp1);
  
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        for (Blueprint B : BS.getAllBlueprints()) {
        	for (Point P : B.getPoints()) {
        		System.out.println("(" + P.getX() + "-" + P.getY() + ")");
        	}
        }
        System.out.println("Despues de filtrar:");
        BS.filtrar();
        
        for (Blueprint B : BS.getAllBlueprints()) {
        	for (Point P : B.getPoints()) {
        		System.out.println("(" + P.getX() + "-" + P.getY() + ")");
        	}
        }
    }

}
