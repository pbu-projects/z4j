package lol.pbu.z4j.client;

import io.micronaut.core.annotation.Nullable;
import io.micronaut.http.annotation.*;
import io.micronaut.http.annotation.Post;
import io.micronaut.http.client.annotation.Client;
import io.micronaut.retry.annotation.Retryable;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import lol.pbu.z4j.model.*;
import reactor.core.publisher.Mono;

@Retryable
@Client("zendesk")
public interface TicketClient {

    /**
     * {@summary Create Ticket}
     *
     * @param ticketCreateRequest (required)
     * @return Create ticket (status code 201)
     */
    @Post("/api/v2/tickets")
    Mono<@Valid TicketResponse> createTicket(
            @Body @NotNull @Valid TicketCreateRequest ticketCreateRequest
    );

    /**
     * {@summary Create Ticket Field}
     * <p>Creates any of the following custom field types:</p> <table>     <thead>     <tr>         <th>Custom field type</th>         <th>Description</th>     </tr>     </thead>     <tbody>     <tr>         <td>text</td>         <td>Default custom field type when <code>type</code> is not specified</td>     </tr>     <tr>         <td>textarea</td>         <td>For multi-line text</td>     </tr>     <tr>         <td>checkbox</td>         <td>To capture a boolean value. Allowed values are true or false. Optionally, you can specify a tag to be added             to the ticket when the value is true.         </td>     </tr>     <tr>         <td>date</td>         <td>Example: 2021-04-16</td>     </tr>     <tr>         <td>integer</td>         <td>String composed of numbers. May contain an optional decimal point</td>     </tr>     <tr>         <td>decimal</td>         <td>For numbers containing decimals</td>     </tr>     <tr>         <td>regexp</td>         <td>Matches the Regex pattern found in the custom field settings</td>     </tr>     <tr>         <td>partialcreditcard</td>         <td>A credit card number. Only the last 4 digits are retained</td>     </tr>     <tr>         <td>multiselect</td>         <td>Enables users to choose multiple options from a dropdown menu. It contains one or more tag values belonging             to the field&#39;s options.         </td>     </tr>     <tr>         <td>tagger</td>         <td>Single-select dropdown menu. It contains one or more tag values belonging to the field&#39;s options.             Example: ( {&quot;id&quot;: 21938362, &quot;value&quot;: [&quot;hd_3000&quot;, &quot;hd_5555&quot;]})         </td>     </tr>     <tr>         <td>lookup</td>         <td>A field to create a relationship (see <a                 href=\"/api-reference/ticketing/lookup_relationships/lookup_relationships/'>lookup relationships</a>) to             another object such as a user, ticket, or organization         </td>     </tr>     </tbody> </table> <p><strong>Note</strong>: Tags can&#39;t be re-used across custom ticket fields. For example, if you configure a tag for     a checkbox field, you can&#39;t use that tag value for a dropdown (tagger) field option. The use of tags isn&#39;t     validated and can prevent editing in the future.</p> <p>See <a href='https://support.zendesk.com/hc/en-us/articles/203661866'>About custom field types</a> in the Zendesk     Help Center.</p> <h4 id=\"allowed-for'>Allowed For</h4> <ul>     <li>Admins</li> </ul> <h4 id=\"field-limits'>Field limits</h4> <p>We recommend the following best practices for ticket fields limits. Creating more than these amounts can affect     performance.</p> <ul>     <li>400 ticket fields per account if your account doesn&#39;t have ticket forms</li>     <li>400 ticket fields per ticket form if your account has ticket forms</li> </ul>
     *
     * @return Created response (status code 201)
     */
    @Post("/api/v2/ticket_fields")
    Mono<@Valid TicketFieldResponse> createTicketField();

    /**
     * {@summary Count Tickets}
     * Returns an approximate count of tickets in the account. If the count exceeds 100,000, it is updated every 24 hours. <br><br> {@code ccd} lists tickets that the specified user is cc'd on. <br><br> The {@code count[refreshed_at]} property is a timestamp that indicates when the count was last updated. <br><br> <strong>Note</strong>: When the count exceeds 100,000, {@code count[refreshed_at]} may occasionally be null. This indicates that the count is being updated in the background, and {count[value]} is limited to 100,000 until the update is complete.  <h4>Allowed For</h4> <ul>   <li>Agents</li> </ul>
     *
     * @return Count of tickets (status code 200)
     */
    @Get("/api/v2/tickets/count")
    Mono<@Valid TicketCountResponse> getTicketCount();

    /**
     * {@summary List Ticket Fields}
     * <p>Returns a list of all system and custom ticket fields in your account.</p> <p>For end users, only the ticket fields with visible_in_portal set to true are returned.</p> <p>Consider caching this resource to use with the{@link TicketClient}.</p> <h4 id=\"allowed-for'>Allowed For</h4> <ul>     <li>Anyone</li> </ul>
     *
     * @param locale  Forces the {@code titleInPortal} property to return a dynamic content variant for the specified locale. Only accepts {@link LocaleClient#listLocales() active locale ids}.  (optional)
     * @param creator Includes the {@code creatorUserId} and {@code creatorAppName} properties in the response. If the ticket field is created  by an app, {@code creatorAppName} is the name of the app and {@code creatorUserId} is {@code -1}. If the ticket field  is not created by an app, {@code creatorAppName} is null.  (optional)
     * @return Success response (status code 200)
     */
    @Get("/api/v2/ticket_fields")
    Mono<@Valid TicketFieldsResponse> listTicketFields(
            @QueryValue("locale") @Nullable String locale,
            @QueryValue("creator") @Nullable Boolean creator
    );

    /**
     * {@summary List Tickets}
     *
     * @param externalId Lists tickets by external id. External ids don't have to be unique for each ticket. As a result, the request may return multiple tickets with the same external id. (optional)
     * @return List tickets (status code 200)
     */
    @Get("/api/v2/tickets")
    Mono<@Valid TicketsResponse> listTickets(
            @QueryValue("external_id") @Nullable String externalId
    );

    /**
     * {@summary Show Ticket}
     * <p>Returns a number of ticket properties though not the ticket comments. To get the comments, use <a href='developer.zendesk.com/api-reference/ticketing/tickets/ticket_comments/#list-comments'>List Comments</a></p> <h4 id=\"allowed-for'>Allowed For</h4> <ul>     <li>Agents</li> </ul>
     *
     * @param ticketId The ID of the ticket (required)
     * @return Ticket (status code 200)
     */
    @Get("/api/v2/tickets/{ticket_id}")
    Mono<@Valid TicketResponse> showTicket(
            @PathVariable("ticket_id") @NotNull Long ticketId
    );

    /**
     * {@summary Update Ticket}
     *
     * @param ticketId            The ID of the ticket (required)
     * @param ticketUpdateRequest (optional)
     * @return Successful request (status code 200)
     */
    @Put("/api/v2/tickets/{ticket_id}")
    Mono<@Valid TicketUpdateResponse> updateTicket(
            @PathVariable("ticket_id") @NotNull Long ticketId,
            @Body @Nullable @Valid TicketUpdateRequest ticketUpdateRequest
    );
}