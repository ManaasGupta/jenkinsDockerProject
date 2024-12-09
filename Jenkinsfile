pipeline {
    agent any

    environment {
        SONARQUBE_URL = "http://<SonarQube_IP>:9000" // Replace with the actual IP/hostname
        SONARQUBE_TOKEN = credentials('sonarqube-token') // Jenkins Credential ID for SonarQube token
    }

    stages {
        stage('Checkout Code') {
            steps {
                checkout scm
            }
        }

        stage('Build') {
            steps {
                sh 'mvn clean install'
            }
        }

        stage('SonarQube Analysis') {
            steps {
                withSonarQubeEnv('SonarQube') { // Use the SonarQube server name configured in Jenkins
                    sh '''
                        mvn sonar:sonar \
                        -Dsonar.projectKey=demo \
                        -Dsonar.host.url=${SONARQUBE_URL} \
                        -Dsonar.login=${SONARQUBE_TOKEN}
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 1, unit: 'MINUTES') {
                    script {
                        def qg = waitForQualityGate()
                        if (qg.status != 'OK') {
                            error "Pipeline aborted due to quality gate failure: ${qg.status}"
                        }
                    }
                }
            }
        }
    }
}
