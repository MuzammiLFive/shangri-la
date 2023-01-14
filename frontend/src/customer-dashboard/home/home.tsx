import React from "react";

export class Home extends React.Component<any, any> {
    render() {
        const user = sessionStorage.getItem("user");
        return (
            <div className="sub">Welcome {user} !</div>
        )
    }
}
