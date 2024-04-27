// SPDX-FileCopyrightText: 2024 Ascensio System SIA
//
// SPDX-License-Identifier: Apache-2.0

package com.onlyoffice.integration.documentserver.util;

import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import org.springframework.stereotype.Component;
/**
 * Disables and enables certificate and host-name checking in
 * HttpsURLConnection, the default JVM implementation of the HTTPS/TLS protocol.
 * Has no effect on implementations such as Apache Http Client, Ok Http.
*/
@Component
public final class SSLUtils {

    private final HostnameVerifier jvmHostnameVerifier = HttpsURLConnection.getDefaultHostnameVerifier();

    private final HostnameVerifier trivialHostnameVerifier = new HostnameVerifier() {
        public boolean verify(final String hostname, final SSLSession sslSession) {
            return true;
        }
    };

    private final TrustManager[] unquestioningTrustManager = new TrustManager[] {
            new X509TrustManager() {
        public java.security.cert.X509Certificate[] getAcceptedIssuers() {
            return null;
        }

        public void checkClientTrusted(final X509Certificate[] certs, final String authType) {
        }

        public void checkServerTrusted(final X509Certificate[] certs, final String authType) {
        }
    } };

    public void turnOffSslChecking() throws NoSuchAlgorithmException, KeyManagementException {
        HttpsURLConnection.setDefaultHostnameVerifier(trivialHostnameVerifier);
        // Install the all-trusting trust manager
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, unquestioningTrustManager, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

    public void turnOnSslChecking() throws KeyManagementException, NoSuchAlgorithmException {
        HttpsURLConnection.setDefaultHostnameVerifier(jvmHostnameVerifier);
        // Return it to the initial state (discovered by reflection, now hardcoded)
        SSLContext sc = SSLContext.getInstance("SSL");
        sc.init(null, null, null);
        HttpsURLConnection.setDefaultSSLSocketFactory(sc.getSocketFactory());
    }

}
