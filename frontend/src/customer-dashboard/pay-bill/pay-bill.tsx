import React from "react";
import "./pay-bill.css";

export class PayBill extends React.Component<any, any> {
    render() {
        return (
            <div>
                <div className="sub sub2">Bill Payment</div>
                <div className="payment">
                    <div className="patch">Bill</div>
                    <div className="patch">
                        <div>Balance</div>
                    </div>
                </div>
            </div>
        )
    }
}
