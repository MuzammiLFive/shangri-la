import React from "react";
import './login.css';
import {preloginPost} from "../common/http";

interface loginState {
    emailError?: string;
    passwordError?: string;
}

interface loginResponse {
    customerId: string,
    role: string,
    token: string,
}

export class Login extends React.Component<any, loginState> {
    constructor(props: any) {
        super(props);
        this.state = {
            emailError: "",
            passwordError: ""
        }
    }

    login() {
        const email = (document.getElementById("email") as HTMLInputElement).value;
        if (email === "") {
            alert("Email cannot be empty");
            return;
        }
        const password = (document.getElementById("password") as HTMLInputElement).value;
        if (password === "") {
            alert("password cannot be empty");
            return;
        }

        preloginPost("/api/login", {"email": email, "password": password})
            .then(response => {
                if (!response?.ok) {
                    alert("Login error");
                    throw new Error("Login error");
                }
                return response?.json() as unknown as loginResponse
            }).then(json => {
            sessionStorage.setItem('user', json.customerId);
            sessionStorage.setItem('role', json.role);
            sessionStorage.setItem('token', json.token);
            window.location.reload();
        }).catch(error => {
            console.log(error);
        });
    }

    toggleError(hidden: boolean) {
        const element = document.getElementById("error") as HTMLDivElement;
        element.hidden = hidden;
    }

    render() {
        return (
            <div className="login">
                <div className="cell">
                    <div>Email id: <input id="email" type="email" required onClick={() => this.toggleError(true)}/>
                    </div>
                </div>
                <div className="cell">
                    <div>Password: <input id="password" type="password" required
                                          onClick={() => this.toggleError(true)}/></div>
                </div>
                <div id="error" hidden>
                    Invalid username or password
                </div>
                <div className="cell">
                    <div><input type="submit" value="Log in" onClick={() => this.login()}/></div>
                </div>
            </div>
        );
    }
}
