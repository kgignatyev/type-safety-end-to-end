echo "enter your docker password"

read dpwd
#
#kubectl create secret docker-registry private-pull \
#   --docker-server=<your-registry-server \
#   --docker-username=kgignatyev \
#   --docker-password=$dpwd \
#   --docker-email=kgignatyev@gmail.com

kubectl create secret docker-registry private-pull \
   --docker-server=index.docker.io \
   --docker-username=kgignatyev \
   --docker-password=$dpwd \
   --docker-email=kgignatyev@gmail.com

