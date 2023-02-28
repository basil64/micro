pipeline {
    agent any
    tools { 
        maven 'maven' 
        jdk 'JDK9' 
    }
    stages {
        stage('Build') {
            steps {
                echo 'Building..'
                   sh 'mvn clean install'
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
