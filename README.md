# URL Shortener - Backend

> This is a personal project used for my Junior Software Engineer application at Synergie Global. It is a simple URL
> shortener application that allows users to shorten URLs and manage them.

> Frontend repository: [urlshortener-frontend](https://github.com/southsea2410/urlshortener-frontend)

[![wakatime](https://wakatime.com/badge/github/southsea2410/urlshortener-backend.svg)](https://wakatime.com/badge/github/southsea2410/urlshortener-backend)

### Requirement

- Java 17
- Maven 3.8.3

### Guides

> Warning: Environment variables are required to run the application. Please refer to the `stack.env.example` file for
> more information.

#### Docker run guide

- Rename `stack.env.example` to `stack.env`
- Run the following command to build the image

```bash
docker build -t urlshortener-backend .
```

- Run the following command to run the container

```bash
docker run --env-file stack.env -p 4002:4002 urlshortener-backend
```

#### Local run guide (IntelliJ IDEA)

- Rename `stack.env.example` to `stack.env`
- Add `stack.env` to the environment variables in the run configuration
- Run the application
