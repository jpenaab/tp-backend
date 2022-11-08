/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoplaya.security.auth;

import java.io.IOException;
import java.util.Map;

import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.FailedLoginException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

/**
 *
 * @author u$3R
 */
public class JAASTPLoginModule implements LoginModule {

    private Subject subject;
    private CallbackHandler callbackHandler;
    private Map sharedState;
    private Map options;

    private UserPrincipal userPrincipal;
    private RolePrincipal rolePrincipal;
    
    // username and password
    private String username;
    private char[] password;
  
    // the authentication status
    private boolean succeeded = false;
    private boolean commitSucceeded = false;

    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {

        System.out.println("Login Module - initialize called");
        
        this.subject = subject;
        this.callbackHandler = callbackHandler;
        this.sharedState = sharedState;
        this.options = options;

        System.out.println("testOption value: " + (String) options.get("testOption"));
    }

    @Override
    public boolean login() throws LoginException {
        
        System.out.println("Login Module - login called");
        
        if (callbackHandler == null) {
            throw new LoginException("Error: no CallbackHandler available " +
                        "to garner authentication information from the user");
        }

        Callback callbacks[] = new Callback[2];
        callbacks[0] = new NameCallback("username");
        callbacks[1] = new PasswordCallback("password", false);
       
        try {
            callbackHandler.handle(callbacks);
            username = ((NameCallback)callbacks[0]).getName();
            char[] tmpPassword = ((PasswordCallback)callbacks[1]).getPassword();
            
            if (tmpPassword == null) {
                // treat a NULL password as an empty password
                tmpPassword = new char[0];
            }
            
            password = new char[tmpPassword.length];
            System.arraycopy(tmpPassword, 0,
                        password, 0, tmpPassword.length);
            ((PasswordCallback)callbacks[1]).clearPassword();
            
        } catch (IOException e) {
            throw new LoginException(e.toString());
        } catch (UnsupportedCallbackException e) {
            throw new LoginException(e.toString());
        } catch (NullPointerException e){
            throw new LoginException(e.toString());
        }
        
        // print debugging information
        if (true) {
            System.out.println("\t\t[SampleLoginModule] " +
                                "user entered user name: " +
                                username);
            System.out.print("\t\t[SampleLoginModule] " +
                                "user entered password: ");
            for (int i = 0; i < password.length; i++)
                System.out.print(password[i]);
            System.out.println();
        }

       // verify the username/password
        boolean usernameCorrect = false;
        boolean passwordCorrect = false;
        if (username.equals("testUser"))
            usernameCorrect = true;
        if (usernameCorrect &&
            password.length == 12 &&
            password[0] == 't' &&
            password[1] == 'e' &&
            password[2] == 's' &&
            password[3] == 't' &&
            password[4] == 'P' &&
            password[5] == 'a' &&
            password[6] == 's' &&
            password[7] == 's' &&
            password[8] == 'w' &&
            password[9] == 'o' &&
            password[10] == 'r' &&
            password[11] == 'd') {

            // authentication succeeded!!!
            passwordCorrect = true;
            if (true)
                System.out.println("\t\t[SampleLoginModule] " +
                                "authentication succeeded");
            succeeded = true;
            return true;
        } else {

            // authentication failed -- clean out state
            if (true)
                System.out.println("\t\t[SampleLoginModule] " +
                                "authentication failed");
            succeeded = false;
            username = null;
            for (int i = 0; i < password.length; i++)
                password[i] = ' ';
            password = null;
            if (!usernameCorrect) {
                throw new FailedLoginException("User Name Incorrect");
            } else {
                throw new FailedLoginException("Password Incorrect");
            }
        }
    }

    @Override
    public boolean commit() throws LoginException {
        
        if (succeeded == false) {
            return false;
        } else {
            // add a Principal (authenticated identity)
            // to the Subject

            // assume the user we authenticated is the SamplePrincipal
            userPrincipal = new UserPrincipal(username);
            
            if (!subject.getPrincipals().contains(userPrincipal))
                subject.getPrincipals().add(userPrincipal);

            if (true) {
                System.out.println("\t\t[SampleLoginModule] " +
                                "added SamplePrincipal to Subject");
            }

            // in any case, clean out state
            username = null;
            
            for (int i = 0; i < password.length; i++)
                password[i] = ' ';
            password = null;

            commitSucceeded = true;
            return true;
        }
    }

    @Override
    public boolean abort() throws LoginException {
        
        if (succeeded == false) {
            return false;
        } else if (succeeded == true && commitSucceeded == false) {
            // login succeeded but overall authentication failed
            succeeded = false;
            username = null;
            if (password != null) {
                for (int i = 0; i < password.length; i++)
                    password[i] = ' ';
                password = null;
            }
            userPrincipal = null;
        } else {
            // overall authentication succeeded and commit succeeded,
            // but someone else's commit failed
            logout();
        }
        return true;
        
    }

    @Override
    public boolean logout() throws LoginException {
        
        subject.getPrincipals().remove(userPrincipal);
        succeeded = false;
        succeeded = commitSucceeded;
        username = null;
        
        if (password != null) {
            for (int i = 0; i < password.length; i++)
                password[i] = ' ';
            password = null;
        }
        
        userPrincipal = null;
        return true;
        
    }

}
