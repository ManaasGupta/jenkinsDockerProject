pipeline {
    agent any

    tools {
        maven 'Maven 3.8.6' // Ensure Maven is configured in Jenkins
    }

    environment {
        SONARQUBE_URL = "http://localhost:9000" // Update with actual SonarQube URL if needed
        SONARQUBE_TOKEN = credentials('sonar-token') // Jenkins Credential ID for SonarQube token
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
                withSonarQubeEnv('SonarQube') { // Use the SonarQube server name configured in Jenkins
                    sh '''
                        mvn clean verify sonar:sonar \
                        -Dsonar.projectKey=jenkinsproject \
                        -Dsonar.projectName="jenkinsproject" \
                        -Dsonar.host.url=${SONARQUBE_URL} \
                        -Dsonar.login=${SONARQUBE_TOKEN}
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 2, unit: 'MINUTES') { // Adjust timeout as needed
                    script {
                        def qualityGate = waitForQualityGate() // Waits for the SonarQube analysis result
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