<xsl:stylesheet version="1.0"
                xmlns:xsl="http://www.w3.org/1999/XSL/Transform"
                xmlns:xalan="http://xml.apache.org/xalan"
                xmlns:ggq="xalan://ch.hftm.ggq.xsl.GGQ">
    <xsl:output method="xml"/>
    <xsl:param name="BASE_PACKAGE" select="'ch.example'"/>
    <xsl:param name="PROJECT_NAME" select="'code-with-quarkus'"/>
    <xsl:template match="/">
        <project xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 https://maven.apache.org/xsd/maven-4.0.0.xsd"
                 xmlns="http://maven.apache.org/POM/4.0.0"
                 xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance">
            <modelVersion>4.0.0</modelVersion>
            <groupId>
                <xsl:value-of select="$BASE_PACKAGE"/>
            </groupId>
            <artifactId>
                <xsl:value-of select="ggq:toLowerCase($PROJECT_NAME)"/>
            </artifactId>
            <version>1.0.0-SNAPSHOT</version>
            <properties>
                <compiler-plugin.version>3.8.1</compiler-plugin.version>
                <maven.compiler.parameters>true</maven.compiler.parameters>
                <maven.compiler.source>1.8</maven.compiler.source>
                <maven.compiler.target>1.8</maven.compiler.target>
                <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
                <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>
                <quarkus-plugin.version>1.11.3.Final</quarkus-plugin.version>
                <quarkus.platform.artifact-id>quarkus-universe-bom</quarkus.platform.artifact-id>
                <quarkus.platform.group-id>io.quarkus</quarkus.platform.group-id>
                <quarkus.platform.version>1.11.3.Final</quarkus.platform.version>
                <surefire-plugin.version>3.0.0-M5</surefire-plugin.version>
            </properties>
            <dependencyManagement>
                <dependencies>
                    <dependency>
                        <groupId>${quarkus.platform.group-id}</groupId>
                        <artifactId>${quarkus.platform.artifact-id}</artifactId>
                        <version>${quarkus.platform.version}</version>
                        <type>pom</type>
                        <scope>import</scope>
                    </dependency>
                </dependencies>
            </dependencyManagement>
            <dependencies>
                <dependency>
                    <groupId>io.quarkus</groupId>
                    <artifactId>quarkus-hibernate-validator</artifactId>
                </dependency>
                <dependency>
                    <groupId>io.quarkus</groupId>
                    <artifactId>quarkus-resteasy-reactive-jsonb</artifactId>
                </dependency>
                <dependency>
                    <groupId>io.quarkus</groupId>
                    <artifactId>quarkus-resteasy-reactive</artifactId>
                </dependency>
                <dependency>
                    <groupId>com.fasterxml.jackson.datatype</groupId>
                    <artifactId>jackson-datatype-jsr310</artifactId>
                </dependency>
                <dependency>
                    <groupId>io.quarkus</groupId>
                    <artifactId>quarkus-mongodb-client</artifactId>
                </dependency>
                <dependency>
                    <groupId>io.quarkus</groupId>
                    <artifactId>quarkus-mongodb-panache</artifactId>
                </dependency>
                <dependency>
                    <groupId>io.quarkus</groupId>
                    <artifactId>quarkus-arc</artifactId>
                </dependency>
                <dependency>
                    <groupId>io.quarkus</groupId>
                    <artifactId>quarkus-smallrye-openapi</artifactId>
                </dependency>
<!--                <dependency>-->
<!--                    <groupId>io.quarkus</groupId>-->
<!--                    <artifactId>quarkus-elytron-security-properties-file</artifactId>-->
<!--                </dependency>-->
                <dependency>
                    <groupId>io.quarkus</groupId>
                    <artifactId>quarkus-oidc</artifactId>
                </dependency>
                <dependency>
                    <groupId>io.quarkus</groupId>
                    <artifactId>quarkus-junit5</artifactId>
                    <scope>test</scope>
                </dependency>
                <dependency>
                    <groupId>io.rest-assured</groupId>
                    <artifactId>rest-assured</artifactId>
                    <scope>test</scope>
                </dependency>
                <dependency>
                    <groupId>org.mockito</groupId>
                    <artifactId>mockito-all</artifactId>
                    <version>1.10.19</version>
                    <scope>test</scope>
                </dependency>
                <dependency>
                    <groupId>org.jboss.logmanager</groupId>
                    <artifactId>log4j2-jboss-logmanager</artifactId>
                </dependency>
                <dependency>
                    <groupId>org.assertj</groupId>
                    <artifactId>assertj-core</artifactId>
                    <version>3.18.1</version>
                    <scope>test</scope>
                </dependency>
            </dependencies>
            <build>
                <plugins>
                    <plugin>
                        <groupId>io.quarkus</groupId>
                        <artifactId>quarkus-maven-plugin</artifactId>
                        <version>${quarkus-plugin.version}</version>
                        <extensions>true</extensions>
                        <executions>
                            <execution>
                                <goals>
                                    <goal>build</goal>
                                    <goal>generate-code</goal>
                                    <goal>generate-code-tests</goal>
                                </goals>
                            </execution>
                        </executions>
                    </plugin>
                    <plugin>
                        <artifactId>maven-compiler-plugin</artifactId>
                        <version>${compiler-plugin.version}</version>
                    </plugin>
                    <plugin>
                        <artifactId>maven-surefire-plugin</artifactId>
                        <version>${surefire-plugin.version}</version>
                        <configuration>
                            <systemPropertyVariables>
                                <java.util.logging.manager>org.jboss.logmanager.LogManager</java.util.logging.manager>
                                <maven.home>${maven.home}</maven.home>
                            </systemPropertyVariables>
                        </configuration>
                    </plugin>
                    <plugin>
                        <artifactId>maven-surefire-plugin</artifactId>
                        <version>2.22.0</version>
                        <configuration>
                            <excludes>
                                <exclude>**/*IT</exclude>
                            </excludes>
                        </configuration>
                    </plugin>
                    <plugin>
                        <groupId>org.jacoco</groupId>
                        <artifactId>jacoco-maven-plugin</artifactId>
                        <version>0.8.6</version>
                        <configuration>
                            <excludes>
                                <exclude>**/domain/**/*</exclude>
                                <exclude>**/rest/exceptions/**/*</exclude>
                                <exclude>**/rest/dto/**/*</exclude>
                                <exclude>**/repository/impl/**/*</exclude>
                            </excludes>
                        </configuration>
                        <executions>
                            <execution>
                                <goals>
                                    <goal>prepare-agent</goal>
                                </goals>
                            </execution>
                            <!-- attached to Maven test phase -->
                            <execution>
                                <id>report</id>
                                <phase>test</phase>
                                <goals>
                                    <goal>report</goal>
                                </goals>
                            </execution>
                        </executions>
                    </plugin>
                </plugins>
            </build>
            <profiles>
                <profile>
                    <id>integration-tests</id>
                    <build>
                        <plugins>
                            <plugin>
                                <artifactId>maven-surefire-plugin</artifactId>
                                <version>2.22.2</version>
                                <executions>
                                    <execution>
                                        <goals>
                                            <goal>test</goal>
                                        </goals>
                                        <configuration>
                                            <systemProperties>
                                                <property>
                                                    <name>DATA_STORE_BASE_PATH</name>
                                                    <value>${project.build.testOutputDirectory}</value>
                                                </property>
                                            </systemProperties>
                                            <excludes>
                                                <exclude>none</exclude>
                                            </excludes>
                                            <includes>
                                                <include>**/*IT.java</include>
                                            </includes>
                                        </configuration>
                                    </execution>
                                </executions>
                            </plugin>
                            <plugin>
                                <groupId>org.apache.maven.plugins</groupId>
                                <artifactId>maven-pmd-plugin</artifactId>
                                <version>3.14.0</version>
                            </plugin>
                        </plugins>
                    </build>
                </profile>
                <profile>
                    <id>native</id>
                    <activation>
                        <property>
                            <name>native</name>
                        </property>
                    </activation>
                    <build>
                        <plugins>
                            <plugin>
                                <artifactId>maven-failsafe-plugin</artifactId>
                                <version>${surefire-plugin.version}</version>
                                <executions>
                                    <execution>
                                        <goals>
                                            <goal>integration-test</goal>
                                            <goal>verify</goal>
                                        </goals>
                                        <configuration>
                                            <systemPropertyVariables>
                                                <native.image.path>
                                                    ${project.build.directory}/${project.build.finalName}-runner
                                                </native.image.path>
                                                <java.util.logging.manager>org.jboss.logmanager.LogManager
                                                </java.util.logging.manager>
                                                <maven.home>${maven.home}</maven.home>
                                            </systemPropertyVariables>
                                        </configuration>
                                    </execution>
                                </executions>
                            </plugin>
                        </plugins>
                    </build>
                    <properties>
                        <quarkus.package.type>native</quarkus.package.type>
                    </properties>
                </profile>
            </profiles>
        </project>
    </xsl:template>
</xsl:stylesheet>