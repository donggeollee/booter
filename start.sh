echo "recognize start.sh" &&
echo "recognize start.sh" &&
echo "recognize start.sh" &&
echo "recognize start.sh" &&
echo "recognize start.sh" &&
echo "recognize start.sh" &&
echo "recognize start.sh" &&
echo "recognize start.sh" &&
echo "recognize start.sh" &&
docker build -t booter-image . && 
docker run -d -p 8080:8080 --name booter booter-image
