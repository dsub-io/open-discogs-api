FROM scratch
COPY ./build/native/nativeCompile/api /api
ENTRYPOINT ["/api"]