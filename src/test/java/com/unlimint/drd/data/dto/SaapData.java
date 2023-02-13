package com.unlimint.drd.data.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@JsonIgnoreProperties(ignoreUnknown = true)
public class SaapData {

    @JsonProperty("DRC_BACKEND_DOMAIN_URL")
    private String drcBackendDomainUrl;

    @JsonProperty("DRC_BACKEND_PREFIX")
    private String drcBackendPrefix;

    @JsonProperty("DRC_DEBUG")
    private String drcDebug;

    @JsonProperty("DRC_EMAIL_LINK_DOMAIN")
    private String drcEmailLinkDomain;

    @JsonProperty("DRC_HOST_FROM_EMAIL")
    private String drcHostFromEmail;

    @JsonProperty("DRC_KEYCLOAK_CLIENT_ID")
    private String drcKeycloakClientId;

    @JsonProperty("DRC_KEYCLOAK_REALM_ID")
    private String drcKeycloakRealmId;

    @JsonProperty("DRC_KEYCLOAK_SECRET")
    private String drcKeycloakSecret;

    @JsonProperty("DRC_KEYCLOAK_SERVER_URL")
    private String drcKeycloakServerUrl;

    @JsonProperty("DRC_LOG_LEVEL")
    private String drcLogLevel;

    @JsonProperty("DRC_MONGODB_DATABASE")
    private String drcMongodbDatabase;

    @JsonProperty("DRC_MONGODB_HOST")
    private String drcMongodbHost;

    @JsonProperty("DRC_MONGODB_PASSWORD")
    private String drcMongodbPassword;

    @JsonProperty("DRC_MONGODB_SSL")
    private String drcMongodbSsl;

    @JsonProperty("DRC_MONGODB_USER")
    private String drcMongodbUser;

    @JsonProperty("DRC_MONITORING_MEASUREMENT")
    private String drcMonitoringMeasurement;

    @JsonProperty("DRC_MONITORING_RABBITMQ_CONNECTION")
    private String drcMonitoringRabbitmqConnection;

    @JsonProperty("DRC_MONITORING_RABBITMQ_EXCHANGE")
    private String drcMonitoringRabbitmqExchange;

    @JsonProperty("DRC_MONITORING_RABBITMQ_EXCHANGE_DURABLE")
    private String drcMonitoringRabbitmqExchangeDurable;

    @JsonProperty("DRC_MONITORING_RABBITMQ_EXCHANGE_TYPE")
    private String drcMonitoringRabbitmqExchangeType;

    @JsonProperty("DRC_NODEMAILER_HOST")
    private String drcNodemailerHost;

    @JsonProperty("DRC_NODEMAILER_PASS")
    private String drcNodemailerPass;

    @JsonProperty("DRC_NODEMAILER_PORT")
    private String drcNodemailerPort;

    @JsonProperty("DRC_NODEMAILER_SECURE")
    private String drcNodemailerSecure;

    @JsonProperty("DRC_NODEMAILER_SENDER")
    private String drcNodemailerSender;

    @JsonProperty("DRC_NODEMAILER_USER")
    private String drcNodemailerUser;

    @JsonProperty("DRC_QATEAM_RECIPIENTS")
    private String drcQateamRecipients;

    @JsonProperty("DRC_RABBITMQ_CONNECTION")
    private String drcRabbitmqConnection;

    @JsonProperty("DRC_RABBITMQ_DISPUTES_CONNECTION")
    private String drcRabbitmqDisputesConnection;

    @JsonProperty("DRC_RABBITMQ_DISPUTES_DURABLE")
    private String drcRabbitmqDisputesDurable;

    @JsonProperty("DRC_RABBITMQ_DISPUTES_EXCHANGE")
    private String drcRabbitmqDisputesExchange;

    @JsonProperty("DRC_RABBITMQ_DISPUTES_EXCHANGE_TYPE")
    private String drcRabbitmqDisputesExchangeType;

    @JsonProperty("DRC_RABBITMQ_DISPUTES_QUEUE")
    private String drcRabbitmqDisputesQueue;

    @JsonProperty("DRC_RABBITMQ_MAILER_DURABLE")
    private String drcRabbitmqMailerDurable;

    @JsonProperty("DRC_RABBITMQ_MAILER_EXCHANGE")
    private String drcRabbitmqMailerExchange;

    @JsonProperty("DRC_RABBITMQ_MAILER_EXCHANGE_TYPE")
    private String drcRabbitmqMailerExchangeType;

    @JsonProperty("DRC_RABBITMQ_MAILER_QUEUE")
    private String drcRabbitmqMailerQueue;

    @JsonProperty("DRC_RABBITMQ_RATIO_CONNECTION")
    private String drcRabbitmqRatioConnection;

    @JsonProperty("DRC_RABBITMQ_RATIO_DURABLE")
    private String drcRabbitmqRatioDurable;

    @JsonProperty("DRC_RABBITMQ_RATIO_EXCHANGE")
    private String drcRabbitmqRatioExchange;

    @JsonProperty("DRC_RABBITMQ_RATIO_EXCHANGE_TYPE")
    private String drcRabbitmqRatioExchangeType;

    @JsonProperty("DRC_RABBITMQ_RATIO_PREFETCH")
    private String drcRabbitmqRatioPrefetch;

    @JsonProperty("DRC_RABBITMQ_RATIO_QUEUE")
    private String drcRabbitmqRatioQueue;

    @JsonProperty("DRC_RISKTEAM_RECIPIENTS")
    private String drcRiskteamRecipients;

    @JsonProperty("DRC_RISKTEAM_RECIPIENTS_COPIES")
    private String drcRiskteamRecipientsCopies;

    @JsonProperty("DRC_SERVER_CONNECTION_TIMEOUT")
    private String drcServerConnectionTimeout;

    @JsonProperty("DRC_SERVER_HOST")
    private String drcServerHost;

    @JsonProperty("DRC_SERVER_HOST_PORT")
    private String drcServerHostPort;

    @JsonProperty("DRC_SERVER_PROCESS_EXIT_FORCE_TIMEOUT")
    private String drcServerProcessExitForceTimeout;

    @JsonProperty("DRC_SERVER_PROCESS_EXIT_TIMEOUT")
    private String drcServerProcessExitTimeout;

    @JsonProperty("DRC_SERVER_REQUEST_JSON_SIZE")
    private String drcServerRequestJsonSize;

    @JsonProperty("DRC_SERVER_SESSION_SECRET")
    private String drcServerSessionSecret;

    @JsonProperty("DRC_SERVER_WS_PORT")
    private String drcServerWsPort;

    @JsonProperty("DRC_SUPPORT_RECIPIENT_EMAIL")
    private String drcSupportRecipientEmail;

    @JsonProperty("DRC_SUPPORT_RECIPIENT_EMAIL_COPIES")
    private String drcSupportRecipientEmailCopies;
}
