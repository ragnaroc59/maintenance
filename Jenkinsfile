// Lien vers Nexus, doit correspondre à l'instance paramétrée dans Jenkins
def nexusId = 'nexus_localhost'

/* *** Configuration de Nexus pour Maven ***/
// URL de Nexus
def nexusUrl = 'http://172.17.0.4:8081'
def sonarUrl = 'http://172.17.0.3:9000'

// Repo Id (provient du settings.xml nexus pour récupérer user/password)
def mavenRepoId = 'nexusLocal'

/* *** Repositories Nexus *** */
def nexusRepoSnapshot = "maven-snapshots"
def nexusRepoRelease = "maven-releases"



/* *** Détail du projet, récupéré dans le pipeline en lisant le pom.xml *** */
def groupId = ''
def artefactId = ''
def filePath = ''
def packaging = ''
def version = ''

// Variable utilisée pour savoir si c'est une RELEASE ou une SNAPSHOT
def isSnapshot = true

pipeline {
   agent any

   stages {
      stage('Checkout') {
         steps {
            checkout([$class: 'GitSCM', branches: [[name: '*/develop']], doGenerateSubmoduleConfigurations: false, extensions: [], submoduleCfg: [], userRemoteConfigs: [[url: 'https://github.com/ragnaroc59/maintenance.git']]])
         }
      }
      stage('Get info from POM') {
          steps {
            script {
                pom = readMavenPom file: 'pom.xml'
                groupId = pom.groupId
                artifactId = pom.artifactId
                packaging = pom.packaging
                version = pom.version
                filepath = "target/${artifactId}-${version}.jar"
                isSnapshot = version.endsWith("-SNAPSHOT")
            }
            echo groupId
            echo artifactId
            echo packaging
            echo version
            echo filepath
            echo "isSnapshot: ${isSnapshot}"
          }
      }
      stage('Build') {
          steps {
              sh 'mvn clean package'
          }
      }

     stage('SonarQube analysis') {
         steps{
				sh 'mvn sonar:sonar -Dsonar.projectKey=maintenance-back -Dsonar.host.url=http://localhost:9000 -Dsonar.login=057a6cbca037df0ec4586f8cf2a450a50505e524'
             }
       }
      
      /*
      Ce stage ne se lance que si isSnapshot est vrai
      Comme on pousse un Snapshot, on utilise le plugin deploy:deploy-file, cela permet de ne pas mettre les paramètres du Repo dans le pom.xml
      */
      stage('Push SNAPSHOT to Nexus') {
          when { expression { isSnapshot } }
          steps {
              sh "mvn deploy:deploy-file -e -DgroupId=${groupId} -Dversion=${version} -Dpackaging=${packaging} -Durl=${nexusUrl}/repository/${nexusRepoSnapshot}/ -Dfile=${filepath} -DartifactId=${artifactId} -DrepositoryId=${mavenRepoId}"

          }
      }
     
     /*
     Ce stage ne se lance que si isSnapshot est faux
     On pousse la release via le plugin Nexus
     */
      stage('Push RELEASE to Nexus') {
          when { expression { !isSnapshot } }
          steps {
            nexusPublisher nexusInstanceId: 'nexus_localhost', nexusRepositoryId: "${nexusRepoRelease}", packages: [[$class: 'MavenPackage', mavenAssetList: [[classifier: '', extension: '', filePath: "${filepath}"]], mavenCoordinate: [artifactId: "${artifactId}", groupId: "${groupId}", packaging: "${packaging}", version: "${version}"]]]
          }
      }
   }
}