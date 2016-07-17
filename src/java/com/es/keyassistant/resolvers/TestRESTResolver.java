/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.es.keyassistant.resolvers;

import com.pj.client.core.resolvers.RestURIResolver;
import com.pj.client.rest.RestURIPart;
import java.util.List;

/**
 *
 * @author luzhenwen
 */
public class TestRESTResolver extends RestURIResolver{

    @Override
    protected boolean executeAuthorization(List<RestURIPart> parts) {
        return true; //To change body of generated methods, choose Tools | Templates.
    }
    
}
