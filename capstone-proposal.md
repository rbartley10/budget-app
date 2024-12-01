## Capstone Proposal

### 1. Problem Statement
The general world population struggles managing their financial situation either through overspending or lack of budgeting or both. Most users try to budget using spreadsheets like excel, but those can be cumbersome without prior knowledge of excel and can take effort to prepare, and some people try to keep all physical copies of all purchases, bills paid/unpaid, etc. Our app will also users to manage budgeting efficiently with an ease of UI.

### 2. Technical Solution
Create an application that can help users budget their finances more efficiently.

&nbsp;&nbsp;&nbsp;**Scenario 1**<br></br>
&nbsp;&nbsp;&nbsp;Roger wants to get a better sense of his daily spending. He uses the app to log expenses, selecting categories such as food, entertainment, etc. Roger sees a visual representation of how much he spends per category each timePeriod, which helps him adjust his budget and cut unnecessary expenses.
<br></br>
&nbsp;&nbsp;&nbsp;**Scenario 2**<br></br>
&nbsp;&nbsp;&nbsp;Mark Walter struggles with keeping up with his monthly expenses and often finds him self short on money whenever the end of a month approaches. Mark now uses the application to help plan his monthly expenses and prevent financial shortcomings at the end of each month. The application also allows Mark to save some money at the end of each month.

### 3. Key Terms
- Balance <p>&nbsp;&nbsp;&nbsp;An Object that represents a user's overall positive or negative money.</p>
- Expense <p>&nbsp;&nbsp;&nbsp;An Object that counts as a negative balance to a user's overall balance.</p>
- Income <p>&nbsp;&nbsp;&nbsp;An Object that counts as a positive balance to a user's overall balance</p>
- Category <p>&nbsp;&nbsp;&nbsp;An expense or income can have a category such as food, entertainment, etc.</p>
- User <p>&nbsp;&nbsp;&nbsp;A user must sign in and can create budgets.</p>
- Admin <p>&nbsp;&nbsp;&nbsp;Admin has full CRUD for managing user accounts but also has user privelege.</p>
- Advisor <p>&nbsp;&nbsp;&nbsp;Advisor can view and update allowed budgets by users.</p>
- Goal <p>&nbsp;&nbsp;&nbsp;A user can set a goal to reach.</p>

### 4. High Level Requirements
1. User
- Signup for account
- Login to account
- Delete their account
- Create a budget
- Read a budget
- Update a budget
- Delete a budget
- Create a category
- Create a goal
- Optional: Allow Advisor to Update Budget
2. Admin
- All user priveleges
- Create accounts
- Delete accounts
- View users/advisors
3. Advisor
- Read user budgets
- Update Allowed budgets

### 5. User Stories/Scenarios

### User

&nbsp;&nbsp;&nbsp;**Signup for Account**<p>&nbsp;&nbsp;&nbsp;User can interact with UI to input personal information relative to account creation.<br>&nbsp;&nbsp;&nbsp;**Precondition**: User cannot have an existing account<br>&nbsp;&nbsp;&nbsp;**Post-condition**: The account is created.</p>

&nbsp;&nbsp;&nbsp;**Login to Account**<p>&nbsp;&nbsp;&nbsp;User can interact with UI to enter login credentials.<br>&nbsp;&nbsp;&nbsp;**Precondition**: User must have an active account.<br>&nbsp;&nbsp;&nbsp;**Post-condition**: User is authenticated into their account.</p>

&nbsp;&nbsp;&nbsp;**Delete Account**<p>&nbsp;&nbsp;&nbsp;User can interact with UI to deactivate their account.<br>&nbsp;&nbsp;&nbsp;**Precondition**: User must have an active account and be logged in.<br>&nbsp;&nbsp;&nbsp;**Post-condition**: User's account is deactivated and no longer accessible.</p>

&nbsp;&nbsp;&nbsp;**Create a Budget**<p>&nbsp;&nbsp;&nbsp;User can interact with UI to create a new budget.<br>&nbsp;&nbsp;&nbsp;**Precondition**: User must have an active account and be logged in.<br>&nbsp;&nbsp;&nbsp;**Post-condition**: A new budget is created in the user's database.</p>

&nbsp;&nbsp;&nbsp;**Read a Budget**<p>&nbsp;&nbsp;&nbsp;User can select an existing budget on their account to view.<br>&nbsp;&nbsp;&nbsp;**Precondition**: User must have an active account and be logged in.<br>&nbsp;&nbsp;&nbsp;**Post-condition**: Budget is displayed for the user.</p>

&nbsp;&nbsp;&nbsp;**Update a Budget**<p>&nbsp;&nbsp;&nbsp;User can select an existing budget on their account to update.<br>&nbsp;&nbsp;&nbsp;**Precondition**: User must have an active account and be logged in. User selects a budget to update and changes desired values.<br>&nbsp;&nbsp;&nbsp;**Post-condition**: Budget data is updated and user is returned to the view.</p>

&nbsp;&nbsp;&nbsp;**Delete a Budget**<p>&nbsp;&nbsp;&nbsp;User can select an existing budget on their account to delete.<br>&nbsp;&nbsp;&nbsp;**Precondition**: User must have an active account and be logged in. User is prompted to confirm deletion of selected budget.<br>&nbsp;&nbsp;&nbsp;**Post-condition**: Budget is deleted from the user's account.</p>

&nbsp;&nbsp;&nbsp;**Create a Category**<p>&nbsp;&nbsp;&nbsp;User can create a new category for expenses/income.<br>&nbsp;&nbsp;&nbsp;**Precondition**: User must have an active account and be logged in.<br>&nbsp;&nbsp;&nbsp;**Post-condition**: New category is added to the user's category table.</p>

&nbsp;&nbsp;&nbsp;**Create a Goal**<p>&nbsp;&nbsp;&nbsp;User can create a goal for a budget.<br>&nbsp;&nbsp;&nbsp;**Precondition**: User must have an active account and be logged in.<br>&nbsp;&nbsp;&nbsp;**Post-condition**: New goal is added to a budget.</p>

&nbsp;&nbsp;&nbsp;**Optional: Allow Admin Edit Permission**<p>&nbsp;&nbsp;&nbsp;User can mark budgets as editable by admins.<br>&nbsp;&nbsp;&nbsp;**Precondition**: User must have an active account and be logged in and have at least one budget.<br>&nbsp;&nbsp;&nbsp;**Post-condition**: Budget is marked as editable by admin.</p>

### Admin

&nbsp;&nbsp;&nbsp;**Create Accounts**<p>&nbsp;&nbsp;&nbsp;Admin can interact with UI to delete and existing user's account given a reason.<br>&nbsp;&nbsp;&nbsp;**Precondition**: Must to be logged in as an admin. Account can not be existing.<br>&nbsp;&nbsp;&nbsp;**Post-condition**: The account for the new user is created.</p>

&nbsp;&nbsp;&nbsp;**Delete Accounts**<p>&nbsp;&nbsp;&nbsp;User can select an existing budget on their account to view.<br>&nbsp;&nbsp;&nbsp;**Precondition**: Has to be logged in as an admin. Account has to exist.<br>&nbsp;&nbsp;&nbsp;**Post-condition**: The account for the user is deleted.</p>

&nbsp;&nbsp;&nbsp;**View Users/Advisors**<p>&nbsp;&nbsp;&nbsp;Admin can view list of user and advisor accounts.<br>&nbsp;&nbsp;&nbsp;**Precondition**: Has to be logged in as an admin.<br>&nbsp;&nbsp;&nbsp;**Post-condition**: List of users/advisors is viewed.</p>

### Advisor

&nbsp;&nbsp;&nbsp;**Read User Budgets**<p>&nbsp;&nbsp;&nbsp;Advisor can read budgets on a user account.<br>&nbsp;&nbsp;&nbsp;**Precondition**: Must be logged in as an advisor.<br>&nbsp;&nbsp;&nbsp;**Post-condition**: Selected budget is viewed.</p>

&nbsp;&nbsp;&nbsp;**Update User Budget**<p>&nbsp;&nbsp;&nbsp;Advisor can update allowed budgets on a user account.<br>&nbsp;&nbsp;&nbsp;**Precondition**: Must be logged in as an advisor.<br>&nbsp;&nbsp;&nbsp;**Post-condition**: Selected budget can be edited by advisor.</p>