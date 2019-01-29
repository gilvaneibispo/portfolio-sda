/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package portfolio.sda.model;

import java.io.File;
import java.io.Serializable;
import java.util.ArrayList;

/**
 *
 * @author Gilvanei
 */
public class Group implements Serializable{
    
    private String name;
    private String ip;
    
    public void setName(String n){
        name = n;
    }
    
    public void setIpMulcast(String ip){
        this.ip = ip;
    }
    
    public String getName(){
        return name;
    }
}
