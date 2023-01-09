import React from 'react';
import './App.css';
import {NavBar} from "./nav-bar/nav-bar";
import {Authentication} from "./authentication/Authentication";
import {AdminDashboard} from "./admin-dashboard/admin-dashboard";
import {CustomerDashboard} from "./customer-dashboard/customer-dashboard";

function App() {
    const role = sessionStorage.getItem("role");
    return (
        <div className="App">
            <NavBar role={role || undefined}/>
            {role === null || role === undefined ? <Authentication/> : (role === "customer" ? <CustomerDashboard/> :
                <AdminDashboard/>)}
        </div>
    );
}

export default App;
