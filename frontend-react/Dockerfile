FROM nginx:1.22.1
COPY build /usr/share/nginx/html
COPY build_nginx_config/nginx.conf /etc/nginx/nginx.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
