pipeline {
    agent any

    tools {
        // названия должны совпадать с теми, что вы задали в Global Tool Configuration
        maven 'maven'
        jdk 'jdk21'
    }


    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run tests') {
            steps {
                sh 'mvn clean test'
            }
        }

        stage('Allure report') {
            steps {
                allure includeProperties: false,
                       jdk: '',
                       results: [[path: 'target/allure-results']]
            }
        }
    }

    post {
        always {
            junit 'target/surefire-reports/*.xml'
        }
    }
}
