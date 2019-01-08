This module provides a Spring Boot auto-configuration to export actuator metrics to 
Google Cloud's Stackdriver.

The only configuration needed is to set the Spring property
`management.metrics.export.stackdriver.project-id` to reference your GCP project's ID.

If running external to a GCP compute resource, refer to the 
[GCP Monitoring Client documentation](https://cloud.google.com/monitoring/docs/reference/libraries#setting_up_authentication)
and you will need to generate an authentication key file and pass the path to that via the
environment variable `GOOGLE_APPLICATION_CREDENTIALS`.