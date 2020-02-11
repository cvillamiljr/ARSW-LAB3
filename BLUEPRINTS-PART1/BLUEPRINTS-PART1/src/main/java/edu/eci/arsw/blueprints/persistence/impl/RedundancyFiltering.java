/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.eci.arsw.blueprints.persistence.impl;

import edu.eci.arsw.blueprints.model.Blueprint;
import edu.eci.arsw.blueprints.model.Point;
import edu.eci.arsw.blueprints.persistence.filtering;
import java.util.Set;
import org.springframework.stereotype.Service;

/**
 *
 * @author diego.puerto
 */
@Service("RedundancyFiltering")
public class RedundancyFiltering implements filtering{

    @Override
    public Set<Blueprint> filtrar(Set<Blueprint> mapa) {
        for (Blueprint BP : mapa){
            int j = 0;
            while (j < (BP.getPoints().size()-1)){
                if ((BP.getPoints().get(j).getX()==BP.getPoints().get(j+1).getX()) &&  (BP.getPoints().get(j).getY()==BP.getPoints().get(j+1).getY())){
                    BP.getPoints().remove(j+1);
                }
                else j++;
            }
        }
        return mapa;
    }
    
    
    
}
