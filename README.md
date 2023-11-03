# calculate subnets
small java app i found from my apprenticeship 20 years ago to calculate subnets

## start
```bash
javac Subnetting.java
java Subnetting
```

## build jar
```bash
javac Subnetting.java
jar vcfe subnetting.jar Subnetting Subnetting.class ch/philiplehmann/*.class
```

## run jar
```bash
java -jar subnetting.jar
```

## cleanup
```bash
rm -rf **/*.class *.jar
```

## release
```bash
gh release create v1.0.0 subnetting.jar
```