import React from 'react';
import './App.css';
import {NavBar} from "./nav-bar/nav-bar";
import {Authentication} from "./authentication/Authentication";
import {UserDashboard} from "./user-dashboard/user-dashboard";
import {AdminDashboard} from "./admin-dashboard/admin-dashboard";

function App() {
    const role = sessionStorage.getItem("role");
    return (
        <div className="App">
            <NavBar role={role || undefined}/>
            {role === null || role === undefined ? <Authentication/> : (role === "customer" ? <UserDashboard/> :
                <AdminDashboard/>)}
        </div>
    );
}

export default App;
