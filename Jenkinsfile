pipeline {
    agent any

    triggers {
        cron('0 15 * * *')
    }

    parameters {
        choice(
            name: 'TEST_TYPE',
            choices: ['api', 'web'],
            description: 'Выбери тип тестов для запуска'
        )
    }

    stages {
        stage('Checkout') {
            steps {
                checkout scm
            }
        }

        stage('Run tests') {
            steps {
                script {
                    if (params.TEST_TYPE == 'api') {
                        bat 'run-api.test.bat'
                    } else if (params.TEST_TYPE == 'web') {
                        bat 'run-web-test.bat'
                    }
                }
            }
        }

        stage('Allure report') {
            steps {
                bat 'allure-report.bat'
            }
        }
    }

    post {
        always {
            junit 'build/test-results/test/*.xml'
        }
    }
}
