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

        stage('SonarQube Analysis - SAST') {
            steps {
                // Use the SonarQube server configured in Jenkins
                withSonarQubeEnv('SonarQube') { 
                    sh '''
                        mvn clean verify sonar:sonar \
                        -Dsonar.projectKey=jenkinsproject \
                        -Dsonar.projectName="jenkinsproject" \
                        -Dsonar.coverage.jacoco.xmlReportPaths=target/site/jacoco/jacoco.xml
                    '''
                }
            }
        }

        stage('Quality Gate') {
            steps {
                timeout(time: 30, unit: 'MINUTES') {
                    script {
                        def qualityGate = waitForQualityGate() // Wait for the quality gate result from SonarQube
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