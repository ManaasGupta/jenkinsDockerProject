pipeline {
    agent any

    tools {
        maven 'Maven 3.8.6' // Ensure Maven is configured in Jenkins
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
                withSonarQubeEnv('SonarQube') {
                    sh '''mvn clean verify sonar:sonar \
                    -Dsonar.projectKey=jenkinsproject \
                    -Dsonar.projectName="jenkinsproject" \
                    -Dsonar.host.url=http://localhost:9000'''
                }
            }
        }
    }
}