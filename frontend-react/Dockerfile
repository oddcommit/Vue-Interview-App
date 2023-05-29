FROM nginx:1.25.0
COPY build /usr/share/nginx/html
COPY build_nginx_config/default.conf /etc/nginx/conf.d/default.conf
EXPOSE 80
CMD ["nginx", "-g", "daemon off;"]
