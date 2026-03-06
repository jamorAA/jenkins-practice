def increment() {
    echo "incrementing the app version..."
    sh "mvn build-helper:parse-version versions:set -DnewVersion=\\\${parsedVersion.majorVersion}.\\\${parsedVersion.minorVersion}.\\\${parsedVersion.nextIncrementalVersion} versions:commit"
    def matcher = readFile('pom.xml') =~ '<version>(.+)</version>'
    def version = matcher[0][1]
    env.IMAGE_NAME = "$version-$BUILD_NUMBER"
}

def buildJar() {
    echo "building the application..."
    sh "mvn clean package"
}

def buildImage() {
    echo "building the docker image..."
    withCredentials([usernamePassword(credentialsId: 'docker-hub-repo', passwordVariable: 'PASSWORD', usernameVariable: 'USERNAME')]) {
        sh "docker build -t jamoraa/demo-java-maven-app:${IMAGE_TAG} ."
        sh "echo $PASSWORD | docker login -u $USERNAME --password-stdin"
        sh "docker push jamoraa/demo-java-maven-app:${IMAGE_TAG}"
    }
}

def deployApp() {
    echo "deploying the application..."
}
return this
