pipeline {
    agent any

    // Параметры для выбора типа тестов
    parameters {
        choice(
            name: 'TEST_TYPE',
            choices: ['API', 'WEB'],
            description: 'Выберите тип тестов для запуска'
        )
    }

    // Триггер для автозапуска ежедневно в 15:00
    triggers {
        cron('0 15 * * *')
    }

    stages {
        stage('Checkstyle') {
            steps {
                echo 'Запуск Checkstyle...'
                // Запуск bat файла для проверки стиля кода
                bat 'checkstyle.bat'
            }
        }

        stage('Build') {
            steps {
                echo 'Сборка проекта...'
                // Запуск bat файла для сборки
                bat 'build.bat'
            }
        }

        stage('Run Tests') {
            steps {
                script {
                    if (params.TEST_TYPE == 'API') {
                        echo 'Запуск API тестов...'
                        bat 'run-api-test.bat'
                    } else if (params.TEST_TYPE == 'WEB') {
                        echo 'Запуск WEB тестов...'
                        bat 'run-web_test.bat'
                    }
                }
            }
        }

        stage('Allure Report') {
            steps {
                echo 'Генерация Allure отчета...'
                bat 'allure-report.bat'
            }
        }
    }

    post {
        always {
            echo 'Pipeline завершен'
        }
    }
}
