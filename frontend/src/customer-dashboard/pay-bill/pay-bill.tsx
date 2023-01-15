import React from "react";
import "./pay-bill.css";
import {get, post} from "../../common/http";
import {CustomerDetails} from "../home/home";

interface Bill {
    electricityDayUsage: number;
    electricityNightUsage: number;
    gasUsage: number;
    total: number;
}

interface Iprops {
    bill?: Bill;
    balance?: number;
    validVoucher?: boolean;
}

export class PayBill extends React.Component<any, Iprops> {
    constructor(props: any) {
        super(props);
    }

    componentDidMount() {
        this.getBill();
        this.getUserDetails();
    }

    getBill() {
        get("/customer/get-bill")
            .then(res => {
                    if (res?.ok)
                        return res?.json() as unknown as Bill
                    else
                        return undefined
                }
            )
            .then(data => this.setState({bill: data}))
    }

    getUserDetails() {
        get("/customer/details")
            .then(response => response?.json() as unknown as CustomerDetails)
            .then(data => {
                this.setState({
                    balance: data.balance
                });
            });
    }

    checkVoucher() {
        const code = (document.getElementById("code") as unknown as HTMLInputElement).value;
        if (code == "") {
            return;
        }
        get("/api/check-voucher/" + code)
            .then(res => {
                if (res?.ok) {
                    return true;
                }
            }).then(val => {
            if (val) {
                this.setState({validVoucher: true});
            } else {
                this.setState({validVoucher: false});
            }
        });
    }

    payBill() {
        if (!this.state.balance && !this.state.bill?.total) {
            return;
        }
        if (this.state.balance!! < this.state.bill!!.total) {
            alert("Not enough funds");
            return;
        }
        post("/customer/pay-bill", {})
            .then(res => {
                if (res?.ok) {
                    alert("Payment Complete");
                    this.setState({bill: undefined, balance: this.state.balance!! - 200});
                }
            })
    }

    topup() {
        const code = (document.getElementById("code") as unknown as HTMLInputElement).value;
        if (this.state.validVoucher) {
            post("/customer/voucher-topup/" + code, {})
                .then(res => {
                    if (res?.ok) {
                        alert("Topup Complete");
                    }
                    this.setState({balance: this.state.balance!! + 200.0, validVoucher: false})
                })
        }
    }

    render() {
        return (
            <div>
                <div className="sub sub2">Bill Payment</div>
                <div className="payment">
                    <div className="patch">
                        <div><strong>Bill</strong></div>
                        {!!this.state?.bill ? <div className="pay">
                                <span>Total amount to pay: <b>{this.state.bill.total} £</b></span>
                                <div className="payButton">
                                    <button onClick={() => this.payBill()}>Pay</button>
                                </div>
                            </div> :
                            <div className="noPayment">Please submit a new reading for the bill to be generated.</div>}
                    </div>
                    <div className="patch">
                        <div><strong>Balance</strong></div>
                        {!!this.state?.balance ? <div className="balance">
                            Balance: {this.state.balance}
                        </div> : ""}
                        <div className="topup">
                            <div><span>Voucher Code: </span><input id="code" type="text"
                                                                   onChange={() => this.checkVoucher()}/>
                                {!!this.state?.validVoucher ? <span className="checkmark">
    <div className="checkmark_circle"></div>
    <div className="checkmark_stem"></div>
    <div className="checkmark_kick"></div>
</span> : ""}
                            </div>
                            <div className="topupButton">
                                <button onClick={() => this.topup()}>Top-up</button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        )
    }
}
