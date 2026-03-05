def buildJar() {
    echo "building the application..."
    sh "mvn package"
}

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
        sh "docker build -t jamoraa/demo-java-maven-app:2.2 ."
        sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
        sh "docker push jamoraa/demo-java-maven-app:2.2"
    }
}

def deployApp() {
    echo "deploying the application..."
}
return this