/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.tiempoyplaya;

import com.tiempoplaya.model.TDatos;
import com.tiempoplaya.model.TFotografias;
import com.tiempoplaya.model.TGrupos;
import com.tiempoplaya.model.TPlayas;
import com.tiempoplaya.model.TPlayasDistance;
import com.tiempoplaya.model.TUsuarios;
import com.tiempoplaya.persistance.TPHelperDatos;
import com.tiempoplaya.persistance.TPHelperFotografia;
import com.tiempoplaya.persistance.TPHelperPlayas;
import com.tiempoplaya.persistance.TPHelperUsuarios;
import com.tiempoplaya.utils.CoordenadasUtils;
import com.tiempoplaya.utils.ReadProperties;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.imageio.ImageIO;
import uk.me.jstott.jcoord.*;


/**
 *
 * @author jpenaab
 */
public class mainTest {

    public static void main(String[] args) throws NoSuchAlgorithmException, UnsupportedEncodingException, IOException {

        System.out.println("-- > ");  
                
        System.exit(0);
    }

    public static String createHash(String password) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        MessageDigest digest = MessageDigest.getInstance("SHA-256");
        digest.update(password.getBytes("UTF-8"));
        byte byteData[] = digest.digest();
        //convert bytes to hex chars
        StringBuffer sb = new StringBuffer();
        for (int i = 0; i < byteData.length; i++) {
            sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
        }
        return sb.toString();
    }

}
