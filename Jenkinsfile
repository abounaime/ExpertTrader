pipeline {
    agent any

    environment {
        GIT_REPO = 'https://github.com/abounaime/ExpertTrader.git'
    }

    options {
        timeout(time: 20, unit: 'MINUTES')
    }

    stages {
        stage('Checkout') {
            steps {
                script {
                    try {
                        checkout([
                            $class: 'GitSCM',
                            branches: [[name: '*/master']],
                            userRemoteConfigs: [[url: env.GIT_REPO]]
                        ])
                    } catch (Exception e) {
                        error("Failed to checkout repository: ${e.message}")
                    }
                }
            }
        }
        stage('Test & Static Analysis') {
            parallel {
                stage('Unit Tests') {
                    steps {
                        sh 'docker run --rm -v $PWD:/app -w /app gradle:8-jdk17 gradle test'
                    }
                }
                stage('Static Analysis') {
                    steps {
                        sh 'docker run --rm -v $PWD:/app -w /app gradle:8-jdk17 gradle checkstyleMain checkstyleTest'
                    }
                }
            }
        }
        stage('Build') {
            steps {
                sh 'docker run --rm -v $PWD:/app -w /app gradle:8-jdk17 gradle clean build -x test'
            }
        }
    }
}
