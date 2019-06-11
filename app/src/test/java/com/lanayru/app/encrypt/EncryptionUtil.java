/*
 * **************************************************************************************
 *  *
 *  *  Project:        ZXQ
 *  *
 *  *  Copyright ©     2014-2017 Banma Technologies Co.,Ltd
 *  *                  All rights reserved.
 *  *
 *  *  This software is supplied only under the terms of a license agreement,
 *  *  nondisclosure agreement or other written agreement with Banma Technologies
 *  *  Co.,Ltd. Use, redistribution or other disclosure of any parts of this
 *  *  software is prohibited except in accordance with the terms of such written
 *  *  agreement with Banma Technologies Co.,Ltd. This software is confidential
 *  *  and proprietary information of Banma Technologies Co.,Ltd.
 *  *
 *  ***************************************************************************************
 *  *
 *  *  Header Name:
 *  *
 *  *  General Description: Copyright and file header.
 *  *
 *  *  Revision History:
 *  *                           Modification
 *  *   Author                Date(MM/DD/YYYY)   JiraID           Description of Changes
 *  *
 *  *
 *  ***************************************************************************************
 */

package com.lanayru.app.encrypt;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

/**
 * @author xiaofeng
 * @Project ZXQ
 * @date 2016-3-16 Description:encrypt
 */
public class EncryptionUtil {

    public static String encodePassword(String passwd) {
        MessageDigest md;
        byte[] bt = passwd.getBytes();
        String strDes = null;
        try {
            md = MessageDigest.getInstance("SHA-1");
            md.update(bt);
            strDes = bytes2Hex(md.digest());
        } catch (NoSuchAlgorithmException e) {
            return null;
        }
        return strDes;
    }

    public static String encry2String(String pin, String userName, String random) {
        StringBuilder sb = new StringBuilder();
        String sha1 = encodePassword(pin);
        String sha2 = encodePassword(sha1 + userName);
        String sha3 = encodePassword(sha2 + random);
        sb.append(sha3);
        return sb.toString();
    }

    private static String bytes2Hex(byte[] bts) {
        StringBuilder ret = new StringBuilder();
        String tmp;
        for (byte bt : bts) {
            tmp = Integer.toHexString(bt & 0xFF);
            if (tmp.length() == 1) {
                ret.append("0");
            }
            ret.append(tmp);
        }
        return ret.toString();
    }

}
