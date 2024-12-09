pipeline {
    agent any

    tools {
        maven 'Maven 3.8.6' // Ensure Maven is configured in Jenkins
    }

    environment {
        SONARQUBE_URL = "http://sonarqube:9000" // Update with SonarQube container URL
        SONARQUBE_TOKEN = "squ_84256a89645d3c1eb223f5ea555fd751204ea8e2"// Jenkins Credential ID for SonarQube token
    }

    stages {
        stage('Build Artifact') {
            steps {
                sh 'mvn clean package -DskipTests=true'
                archiveArtifacts artifacts: 'target/*.jar', allowEmptyArchive: true
            }
        }

        stage('Test Maven - JUnit') {
            steps {
                sh 'mvn test'
            }
            post {
                always {
                    junit 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Sonarqube Analysis - SAST') {
            steps {
                // Run the SonarQube analysis using Maven directly
                sh '''
                    mvn clean verify sonar:sonar \
                    -Dsonar.projectKey=jenkinsproject \
                    -Dsonar.projectName="jenkinsproject" \
                    -Dsonar.host.url=${SONARQUBE_URL} \
                    -Dsonar.login=${SONARQUBE_TOKEN}
                '''
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') {
                    script {
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