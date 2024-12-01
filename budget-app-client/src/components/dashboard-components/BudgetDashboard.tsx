import ExpenseForm from "../form-components/ExpenseForm";

function BudgetDashboard() {
    return (
        <>
            <div id="budgetBody">
                <div id="budgetLeftSide">
                    <section id="budgetHeader">
                        <h2>Goal</h2>
                        <h2>$2000</h2>
                    </section>
                    <section id="incomeArea">
                    <input
                    type="number"
                    id="income"
                    name="income"
                    placeholder="Income"
                    className="form-control"
                    />
                    <select 
                    name="recurringTimeframe" 
                    id="recurringTimeframe"
                    defaultValue={'Cycle'}
                    disabled
                    className="form-control"
                    >
                        <option value={'DAILY'}>Daily</option>
                        <option value={'WEEKLY'}>Weekly</option>
                        <option value={'MONTHLY'}>Monthly</option>
                        <option value={'YEARLY'}>Yearly</option>
                    </select>
                    </section>
                    <input type="checkbox" id="allowAdvisor" name="allowAdvisor" />
                    <label htmlFor="allowAdvisor">Allow Advisor?</label>
                    <section id="expenseContainer">
                    </section>
                </div>
                <div id="budgetRightSide">
                    <section id="expenseFormContainer">
                        <ExpenseForm/>
                    </section>
                    <section id="pieChartContainer">
                        
                    </section>
                </div>
            </div>
        </>
    )
}
export default BudgetDashboard;