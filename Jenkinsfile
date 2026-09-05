pipeline {
    agent any

    options {
        timestamps()
        disableConcurrentBuilds()
    }

    environment {
        IMAGE_NAME = 'event-driven-processing-platform'
        IMAGE_TAG  = "${BUILD_NUMBER}"
    }

    stages {
        stage('Compile') {
            steps {
                sh 'mvn --batch-mode clean compile'
            }
        }

        stage('Test') {
            steps {
                sh 'mvn --batch-mode test'
            }
            post {
                always {
                    junit allowEmptyResults: true, testResults: 'target/surefire-reports/*.xml'
                }
            }
        }

        stage('Package') {
            steps {
                sh 'mvn --batch-mode package -DskipTests'
                archiveArtifacts artifacts: 'target/*.jar', fingerprint: true
            }
        }

        stage('Docker Build') {
            steps {
                sh 'docker build --tag $IMAGE_NAME:$IMAGE_TAG --tag $IMAGE_NAME:latest .'
            }
        }
    }
}