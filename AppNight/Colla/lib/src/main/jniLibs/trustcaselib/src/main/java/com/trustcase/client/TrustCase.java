package com.trustcase.client;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.trustcase.client.api.LogLevel;
import com.trustcase.client.api.Logger;
import com.trustcase.client.api.TrustCaseCredentials;
import com.trustcase.client.impl.TrustCaseClientImpl;

/**
 * Factory class for creating various {@link TrustCaseClient} instances. This is the central entry point for clients,
 * that want to use the TrustCase Web Service. <br>
 * Note, that there exists a {@link Builder} class that provides a fluent api for creation of {@link TrustCaseClient}s.
 *
 * @see Builder
 * @author Gunther Klein
 */
public final class TrustCase {
    /**
     * Property, that represents the http(s) endpoint address pointing to the base url of the TrustCase web service.
     * E.g. https://trustcase.com/api/v1/
     */
    public final static String CLIENT_PROPERTY_API_ENDPOINT = "apiEndpoint";
    /**
     * Credentials related client property: jid
     */
    public final static String CLIENT_PROPERTY_JID = "jid";
    /**
     * Credentials related client property: password
     */
    public final static String CLIENT_PROPERTY_PASSWORD = "password";
    /**
     * Credentials related client property: private key
     */
    public final static String CLIENT_PROPERTY_PRIVATE_KEY = "privateKey";
    /**
     * Credentials related client property: public key
     */
    public final static String CLIENT_PROPERTY_PUBLIC_KEY = "publicKey";
    /**
     * @see LogLevel#OFF
     * @see LogLevel#BASIC
     * @see LogLevel#EXTENDED
     * @see LogLevel#FULL
     */
    public final static String CLIENT_PROPERTY_LOG_LEVEL = "logLevel";

    /**
     * Creates a new {@link TrustCaseClient} for the specified API endpoint. The client will not contain any credentials
     * ({@link TrustCaseCredentials}). So use this if u need to set up a fresh client, that needs to go through
     * registration and account creation process first (which - on success - will provide the client with credentials).
     * 
     * @param apiEndpoint
     *            a http(s) endpoint address pointing to the base url of the trust case web service (e.g.
     *            https://trustcase.com/api/v1/). Note, that the endpoint address is not validated, when creating the
     *            client.
     * @return a newly instantiated {@link TrustCaseClient}, that can be used to call TrustCase service methods.
     */
    public static TrustCaseClient newClient(String apiEndpoint) {
        return newBuilder().apiEndpoint(apiEndpoint).build();
    }

    /**
     * Creates a new {@link TrustCaseClient} for the specified API endpoint and credentials.
     * 
     * @param apiEndpoint
     *            a http(s) endpoint address pointing to the base url of the trust case web service (e.g.
     *            https://trustcase.com/api/v1/). Note, that the endpoint address is not validated, when creating the
     *            client.
     * @param credentials
     *            credentials to configure the client for.
     * @return a newly instantiated {@link TrustCaseClient}, that can be used to call TrustCase service methods.
     */
    public static TrustCaseClient newClient(String apiEndpoint, TrustCaseCredentials credentials) {
        return newBuilder().apiEndpoint(apiEndpoint).credentials(credentials).build();
    }

    /**
     * Creates a new {@link TrustCaseClient} for the specified API endpoint, credentials and log level.
     * 
     * @param apiEndpoint
     *            a http(s) endpoint address pointing to the base url of the trust case web service (e.g.
     *            https://trustcase.com/api/v1/). Note, that the endpoint address is not validated, when creating the
     *            client.
     * @param credentials
     *            credentials to configure the client for. May be null. If null, the client will not contain any
     *            credentials ({@link TrustCaseCredentials}), which is used, if u need to set up a fresh client, that
     *            needs to go through registration and account creation process first (which - on success - will provide
     *            the client with credentials).
     * @param logLevel
     *            level of logging in the client api. If null, logging is off.
     * @param logger
     *            optional logger, that can be specified to control logging (e.g. to console, file, etc) of the rest
     *            calls in the API. If null a platform specific default logger will be used. On Android the default
     *            logger calls android.util.Log.d(). On Java SE default logging uses System.out.println(). See also
     *            logLevel property, with which logging can be controlled.
     * @return a newly instantiated {@link TrustCaseClient}, that can be used to call TrustCase service methods.
     */
    public static TrustCaseClient newClient(String apiEndpoint, TrustCaseCredentials credentials, LogLevel logLevel,
                                            Logger logger) {
        return newBuilder().apiEndpoint(apiEndpoint).credentials(credentials).logLevel(logLevel).logger(logger).build();
    }

    /**
     * Creates a new {@link TrustCaseClient} for the specified properties. For all available properties see the various
     * constants starting with <code>CLIENT_PROPERTY_*</code>. Uses the default logger.
     * 
     * @param properties
     *            see the various constants starting with <code>CLIENT_PROPERTY_*</code>
     * @return a newly instantiated {@link TrustCaseClient}, that can be used to call TrustCase service methods.
     */
    public static TrustCaseClient newClient(Properties properties) {
        return newBuilder().properties(properties).build();
    }

    /**
     * Creates a {@link Builder}, that allows setting up a {@link TrustCaseClient} via a fluent API.
     * 
     * @see Builder
     * @return a {@link Builder}
     */
    public static Builder newBuilder() {
        return new Builder();
    }

    /**
     * Builder for {@link TrustCaseClient}s. Usage, e.g.:
     * 
     * An unauthenticated client (needs to go through registration first):
     * 
     * <pre>
     * String endpoint = ...;
     * TrustCaseClient client = TrustCase.newBuilder().apiEndpoint(endpoint).build();
     * //registration process....
     * client.register(...)
     * client.createAccount(...)
     * </pre>
     * 
     * A client with already existing credentials:
     * 
     * <pre>
     * String endpoint = ...;
     * String jid = ...;
     * String privateKey = ...;
     * String publicKey = ...;
     * String password = ...;
     * TrustCaseClient client = TrustCase.newBuilder().apiEndpoint(endpoint).jid(jid).privateKey(privateKey)
     *         .publicKey(publicKey).password(password).build();
     * client.openRoom(....);
     * </pre>
     * 
     * Note, that at least an apiEndpoint needs to be set in order to create a {@link TrustCaseClient}.
     * 
     * @author Gunther Klein
     */
    public static class Builder {
        private String endpoint;
        private String jid;
        private String password;
        private String privateKey;
        private String publicKey;
        private LogLevel logLevel;
        private Logger logger;
        private Properties properties;
        private Map<String, Object> services;

        /**
         * Will create a {@link TrustCaseClient} using the specified apiEndpoint. The endpoint represents an http(s)
         * endpoint address pointing to the base url of the trust case web service (e.g. https://trustcase.com/api/v1/).
         * Note, that the endpoint address is not validated, when creating the client.
         * 
         * @param apiEndpoint
         *            a http(s) endpoint address pointing to the base url of the trust case web service (e.g.
         *            https://trustcase.com/api/v1/). Note, that the endpoint address is not validated, when creating
         *            the client.
         * @return this instance
         */
        public Builder apiEndpoint(String apiEndpoint) {
            this.endpoint = apiEndpoint;
            return this;
        }

        /**
         * Will create a {@link TrustCaseClient} using the specified {@link TrustCaseCredentials}. If not set for a
         * client, the client will not contain any credentials ( {@link TrustCaseCredentials}), which is used, if u need
         * to set up a fresh client, that needs to go through registration and account creation process first (which -
         * on success - will provide the client with credentials).
         * 
         * @param credentials - credentials to initialize client with
         * @return this instance
         */
        public Builder credentials(TrustCaseCredentials credentials) {
            if (credentials != null) {
                jid = credentials.getJid();
                password = credentials.getPassword();
                privateKey = credentials.getPrivateKey();
                publicKey = credentials.getPublicKey();
            }
            return this;
        }

        /**
         * Will create a {@link TrustCaseClient} using the specified password credential. Note, that if a password is
         * specified also jid, privateKey and publicKey need to be specified.
         * 
         * @param password
         *            password credential
         * @return this instance
         */
        public Builder password(String password) {
            this.password = password;
            return this;
        }

        /**
         * Will create a {@link TrustCaseClient} using the specified jid credential. Note, that if a jid is specified
         * also password, privateKey and publicKey need to be specified.
         * 
         * @param jid
         *            jid credential
         * @return this instance
         */
        public Builder jid(String jid) {
            this.jid = jid;
            return this;
        }

        /**
         * Will create a {@link TrustCaseClient} using the specified private key credential. Note, that if a private key
         * is specified also jid, password, and publicKey need to be specified.
         * 
         * @param privateKey
         *            private key credential
         * @return this instance
         */
        public Builder privateKey(String privateKey) {
            this.privateKey = privateKey;
            return this;
        }

        /**
         * Will create a {@link TrustCaseClient} using the specified public key credential. Note, that if a public key
         * is specified also jid, password, and privateKey need to be specified.
         * 
         * @param publicKey
         *            public key credential
         * @return this instance
         */
        public Builder publicKey(String publicKey) {
            this.publicKey = publicKey;
            return this;
        }

        /**
         * Will create a {@link TrustCaseClient} using the specified log level. If no logLevel is configured, logging
         * will be off.
         * 
         * @param logLevel
         *            level of logging in the client api. If not set, logging is off.
         * @return this instance
         */
        public Builder logLevel(LogLevel logLevel) {
            this.logLevel = logLevel;
            return this;
        }

        /**
         * Will create a {@link TrustCaseClient} using the specified logger. Can be specified to control logging (e.g.
         * to console, file, etc) of the rest calls in the API. If not specified a platform specific default logger will
         * be used. On Android the default logger calls android.util.Log.d(). On Java SE default logging uses
         * System.out.println(). See also logLevel property, with which logging can be controlled.
         * 
         * @param logger
         *            optional logger, that can be specified to control logging (e.g. to console, file, etc) of the rest
         *            calls in the API.
         * @return this instance
         */
        public Builder logger(Logger logger) {
            this.logger = logger;
            return this;
        }

        /**
         * Will create a {@link TrustCaseClient} using the specified properties. For all available properties see the
         * various constants starting with <code>CLIENT_PROPERTY_*</code>. Note, that any properties will be overridden
         * that are configured with any of the explicit methods (like apiEndpoint(), privateKey(), etc.)
         * 
         * @param properties
         *            see the various constants starting with <code>CLIENT_PROPERTY_*</code>
         * @return this instance
         */
        public Builder properties(Properties properties) {
            this.properties = properties;
            return this;
        }

        /**
         * Adds an internal service, that the implementation will run with (e.g. json converter, request interceptor,
         * etc). Note, that this is rather meant for testing purposes and should not be used by production code (it
         * adapts the way the client communicates, implementations may change, etc.)
         * 
         * @return this instance
         */
        public Builder service(String key, Object service) {
            if (services == null) {
                services = new HashMap<>();
            }
            services.put(key, service);
            return this;
        }

        /**
         * Creates the {@link TrustCaseClient} from the provided configuration data.
         * 
         * @return the {@link TrustCaseClient}, that can be used to call TrustCase service methods.
         */
        public TrustCaseClient build() {
            String vEndpoint = computeProperty(CLIENT_PROPERTY_API_ENDPOINT, this.endpoint, properties);
            String vJid = computeProperty(CLIENT_PROPERTY_JID, this.jid, properties);
            String vPassword = computeProperty(CLIENT_PROPERTY_PASSWORD, this.password, properties);
            String vPrivateKey = computeProperty(CLIENT_PROPERTY_PRIVATE_KEY, this.privateKey, properties);
            String vPublicKey = computeProperty(CLIENT_PROPERTY_PUBLIC_KEY, this.publicKey, properties);
            TrustCaseCredentials credentials = null;
            if (vJid != null) {
                credentials = new TrustCaseCredentials(vJid, vPassword, vPrivateKey, vPublicKey);
            }
            LogLevel vLogLevel = this.logLevel;
            if (properties != null && vLogLevel == null) {
                vLogLevel = logLevelFromString(properties.getProperty(CLIENT_PROPERTY_LOG_LEVEL));
            }

            return new TrustCaseClientImpl(vEndpoint, credentials, null, vLogLevel, logger, services);
        }

        private LogLevel logLevelFromString(String logLevelString) {
            if (isEmpty(logLevelString)) {
                return LogLevel.OFF;
            }
            return LogLevel.valueOf(logLevelString.toUpperCase());
        }

        private boolean isEmpty(String s) {
            return s == null || s.length() == 0;
        }

        private String computeProperty(String propertyName, String overriddenValue, Properties props) {
            if (props == null) {
                return overriddenValue;
            }
            String prop = props.getProperty(propertyName);
            if (overriddenValue == null) {
                return prop;
            }
            return overriddenValue;
        }

    }
}
