pipeline {
    agent any
    environment {
        REPO_URL = 'https://maven.minersonline.uk/snapshots'
        REPO_USER = credentials('maven-repo-username')
        REPO_PASSWORD = credentials('maven-repo-password')
    }
    stages {
        stage('Debug') {
            steps {
                echo 'Pipeline script has been loaded and is running.'
                sh 'ls -la' // Optionally, you can check workspace contents
            }
        }
        stage('Checkout') {
            steps {
                git url: 'https://github.com/miners-online/minestom-game-lib', branch: 'main'
            }
        }
        stage('Build with Gradle') {
            steps {
                sh './gradlew clean build'
            }
        }
        stage('Publish Artifacts') {
            steps {
                sh "./gradlew publish"
            }
        }
    }
    post {
        success {
            echo 'Build and publish completed successfully.'
        }
        failure {
            echo 'Build or publish failed.'
        }
    }
}
