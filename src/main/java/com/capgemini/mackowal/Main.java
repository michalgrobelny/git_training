package com.capgemini.mackowal;
import io.javalin.Javalin;
public class Main {
    public static void main(String[] args){
        Javalin app = Javalin.create().start(8000);
        app.get("/", ctx->ctx.result("Hello world"));
        app.get("/user1", ctx->ctx.result("Michal Grobelny"));
    }
}
