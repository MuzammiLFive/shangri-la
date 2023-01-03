import React from "react";
import './nav-bar.css';

interface NavProps {
    role?: string;
}

export class NavBar extends React.Component<NavProps, NavProps> {
    constructor(props: any) {
        super(props);
        this.state = {
            role: this.props.role
        }
    }

    logout() {
        sessionStorage.clear();
        window.location.reload();
    }

    render() {
        console.log(this.state.role);
        return (
            <div className="header">
                <div>iGSE</div>
                {!!this.state.role ?
                    <button className="logout" onClick={this.logout}>Log out</button>
                    : ""}
            </div>
        );
    }
}
