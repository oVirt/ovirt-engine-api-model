/*
The oVirt Project - oVirt Engine Api Model

Copyright oVirt Authors

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

  https://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.

A copy of the Apache License, Version 2.0 is included with the program
in the file ASL2.
*/

package types;

import org.ovirt.api.metamodel.annotations.Type;

@Type
public interface Configuration {
    ConfigurationType type();

    /**
     * The document describing the virtual machine.
     *
     * Example of the OVF document:
     *
     * ```xml
     * <?xml version='1.0' encoding='UTF-8'?>
     * <ovf:Envelope xmlns:ovf="http://schemas.dmtf.org/ovf/envelope/1/"
     *   xmlns:rasd="http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_ResourceAllocationSettingData"
     *   xmlns:vssd="http://schemas.dmtf.org/wbem/wscim/1/cim-schema/2/CIM_VirtualSystemSettingData"
     *   xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     *   ovf:version="3.5.0.0">
     *   <References/>
     *   <Section xsi:type="ovf:NetworkSection_Type">
     *     <Info>List of networks</Info>
     *     <Network ovf:name="Network 1"/>
     *   </Section>
     *   <Section xsi:type="ovf:DiskSection_Type">
     *     <Info>List of Virtual Disks</Info>
     *   </Section>
     *   <Content ovf:id="out" xsi:type="ovf:VirtualSystem_Type">
     *     <CreationDate>2014/12/03 04:25:45</CreationDate>
     *     <ExportDate>2015/02/09 14:12:24</ExportDate>
     *     <DeleteProtected>false</DeleteProtected>
     *     <SsoMethod>guest_agent</SsoMethod>
     *     <IsSmartcardEnabled>false</IsSmartcardEnabled>
     *     <TimeZone>Etc/GMT</TimeZone>
     *     <default_boot_sequence>0</default_boot_sequence>
     *     <Generation>1</Generation>
     *     <VmType>1</VmType>
     *     <MinAllocatedMem>1024</MinAllocatedMem>
     *     <IsStateless>false</IsStateless>
     *     <IsRunAndPause>false</IsRunAndPause>
     *     <AutoStartup>false</AutoStartup>
     *     <Priority>1</Priority>
     *     <CreatedByUserId>fdfc627c-d875-11e0-90f0-83df133b58cc</CreatedByUserId>
     *     <IsBootMenuEnabled>false</IsBootMenuEnabled>
     *     <IsSpiceFileTransferEnabled>true</IsSpiceFileTransferEnabled>
     *     <IsSpiceCopyPasteEnabled>true</IsSpiceCopyPasteEnabled>
     *     <Name>VM_export</Name>
     *     <TemplateId>00000000-0000-0000-0000-000000000000</TemplateId>
     *     <TemplateName>Blank</TemplateName>
     *     <IsInitilized>false</IsInitilized>
     *     <Origin>3</Origin>
     *     <DefaultDisplayType>1</DefaultDisplayType>
     *     <TrustedService>false</TrustedService>
     *     <OriginalTemplateId>00000000-0000-0000-0000-000000000000</OriginalTemplateId>
     *     <OriginalTemplateName>Blank</OriginalTemplateName>
     *     <UseLatestVersion>false</UseLatestVersion>
     *     <Section ovf:id="70b4d9a7-4f73-4def-89ca-24fc5f60e01a"
     *       ovf:required="false"
     *       xsi:type="ovf:OperatingSystemSection_Type">
     *       <Info>Guest Operating System</Info>
     *       <Description>other</Description>
     *     </Section>
     *     <Section xsi:type="ovf:VirtualHardwareSection_Type">
     *       <Info>1 CPU, 1024 Memory</Info>
     *       <System>
     *         <vssd:VirtualSystemType>ENGINE 3.5.0.0</vssd:VirtualSystemType>
     *       </System>
     *       <Item>
     *         <rasd:Caption>1 virtual cpu</rasd:Caption>
     *         <rasd:Description>Number of virtual CPU</rasd:Description>
     *         <rasd:InstanceId>1</rasd:InstanceId>
     *         <rasd:ResourceType>3</rasd:ResourceType>
     *         <rasd:num_of_sockets>1</rasd:num_of_sockets>
     *         <rasd:cpu_per_socket>1</rasd:cpu_per_socket>
     *       </Item>
     *       <Item>
     *         <rasd:Caption>1024 MB of memory</rasd:Caption>
     *         <rasd:Description>Memory Size</rasd:Description>
     *         <rasd:InstanceId>2</rasd:InstanceId>
     *         <rasd:ResourceType>4</rasd:ResourceType>
     *         <rasd:AllocationUnits>MegaBytes</rasd:AllocationUnits>
     *         <rasd:VirtualQuantity>1024</rasd:VirtualQuantity>
     *       </Item>
     *       <Item>
     *         <rasd:Caption>USB Controller</rasd:Caption>
     *         <rasd:InstanceId>3</rasd:InstanceId>
     *         <rasd:ResourceType>23</rasd:ResourceType>
     *         <rasd:UsbPolicy>DISABLED</rasd:UsbPolicy>
     *       </Item>
     *     </Section>
     *   </Content>
     * </ovf:Envelope>
     * ```
     *
     * @author Shmuel Melamud <smelamud@redhat.com>
     * @author Byron Gravenorst <bgraveno@redhat.com>
     * @date 4 Nov 2016
     * @status updated_by_docs
     */
    String data();
}
