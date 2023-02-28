pipeline {
    agent any

    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                with Maven {
                   sh "mvn clean install"
                }    
            }
        }
        stage('Test') {
            steps {
                echo 'Testing..'
            }
        }
        stage('Deploy') {
            steps {
                echo 'Deploying....'
            }
        }
    }
}
