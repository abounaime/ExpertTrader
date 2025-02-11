pipeline {
    agent any

    environment {
        GIT_REPO = 'https://github.com/abounaime/ExpertTrader.git'
        GIT_CREDENTIALS = 'jenkins-credentials-id'
    }

    options {
        timeout(time: 20, unit: 'MINUTES')
        timestamps()
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    try {
                        checkout([
                            $class: 'GitSCM',
                            branches: [[name: '*/main']],
                            userRemoteConfigs: [[url: env.GIT_REPO, credentialsId: env.GIT_CREDENTIALS]]
                        ])
                    } catch (Exception e) {
                        error("Failed to checkout repository: ${e.message}")
                    }
                }
            }
        }
        stage('Build') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('Test') {
            parallel {
                stage('Unit Tests') {
                    steps {
                        sh './gradlew test'
                    }
                }
                stage('Static Analysis') {
                    steps {
                        sh './gradlew checkstyleMain checkstyleTest'
                    }
                }
            }
        }
    }
}
