pipeline {
    agent any

    tools {
        maven 'Maven 3.8.6' // Ensure Maven is configured in Jenkins
    }

    environment {
        SONARQUBE_URL = "http://sonarqube:9000" // SonarQube URL
        SONARQUBE_TOKEN = "sqp_28a26d32ee9adc5c47bc5da37813ee77d7f676b8" // Jenkins Credential ID for SonarQube token
    }

    stages {
        stage('Build Artifact') {
            steps {
                // Clean and package the Maven project
                sh 'mvn clean package -DskipTests=true'
                // Archive the built artifacts
                archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
            }
        }

        stage('Test Maven - JUnit') {
            steps {
                // Run tests
                sh 'mvn test'
            }
            post {
                always {
                    // Publish JUnit test results
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('SonarQube Analysis - SAST') {
            steps {
                // Run SonarQube analysis with JaCoCo coverage report
                sh '''
                    mvn clean verify sonar:sonar \
                    -Dsonar.projectKey=jenkinsDockerProject  \
                    -Dsonar.projectName="jenkinsDockerProject " \
                    -Dsonar.host.url=${SONARQUBE_URL} \
                    -Dsonar.login=${SONARQUBE_TOKEN} \
                    -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                '''
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') { 
                    script {
                        // Wait for SonarQube quality gate result
                        def qualityGate = waitForQualityGate()
                        if (qualityGate.status != 'OK') {
                            error "Pipeline aborted due to quality gate failure: ${qualityGate.status}"
                        }
                    }
                }
            }
        }
    }

    post {
        always {
            echo "Pipeline completed!"
        }
    }
}