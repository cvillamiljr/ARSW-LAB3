/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.model;

import edu.eci.arsw.blueprints.persistence.BlueprintNotFoundException;
import edu.eci.arsw.blueprints.persistence.BlueprintPersistenceException;
import edu.eci.arsw.blueprints.persistence.impl.InMemoryBlueprintPersistence;
import java.util.HashSet;
import java.util.Set;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.fail;

/**
 *
 * @author diego.puerto
 */
public class main {

    public static void main(String a[]) throws BlueprintNotFoundException {
        Set<Blueprint> lista = new HashSet<Blueprint>();
        
        InMemoryBlueprintPersistence ibpp=new InMemoryBlueprintPersistence();
        Point[] pts=new Point[]{new Point(0, 0),new Point(10, 10)};
        Point[] pts1=new Point[]{new Point(30, 30),new Point(40, 40)};
        Point[] pts2=new Point[]{new Point(50, 50),new Point(60, 60)};
        
        Blueprint bp=new Blueprint("john", "thepaint",pts);
        Blueprint bp1=new Blueprint("john", "thepaint1",pts1);
        Blueprint bp2=new Blueprint("john", "thepaint2",pts2);
        
        try {
            ibpp.saveBlueprint(bp);
            ibpp.saveBlueprint(bp1);
            ibpp.saveBlueprint(bp2);
            lista.add(bp);
            lista.add(bp1);
            lista.add(bp2);
            
        } catch (BlueprintPersistenceException ex) {
            fail("Blueprint persistence failed inserting the first blueprint.");
        }
        
        System.out.println("La lista creada directamente deberia mostar los puntos de john:");
        System.out.println(ibpp.getBlueprintsByAuthor("john") + " ");
        System.out.println("Que deberia coincidir con el método usado en el Spring:");
        System.out.println(lista + " ");
    }

}
